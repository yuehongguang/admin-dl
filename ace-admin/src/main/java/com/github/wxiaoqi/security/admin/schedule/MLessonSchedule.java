package com.github.wxiaoqi.security.admin.schedule;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.wxiaoqi.security.admin.biz.IgrowthMCourseBiz;
import com.github.wxiaoqi.security.admin.biz.MLessonBiz;
import com.github.wxiaoqi.security.admin.biz.MediaAdminBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.admin.util.MatrixToImageWriter;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.util.GeoHashKit;

/**
 * 课程定时任务
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Configuration
public class MLessonSchedule {
	
	@Autowired
	private MLessonBiz mLessonBiz;
	
	@Autowired
	private IgrowthMCourseBiz igrowthMCourseBiz;
	
	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	MediaAdminBiz mediaAdminBiz;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	Logger logger = Logger.getLogger(MLessonSchedule.class);
	
	/**
	 *  每隔一分钟查询课程状态 扣除非签到课程的课时数
	 *  -1已经取消预约课程 0 未签到  1 已签到 2 请假  3预  4自选课程 
	 * @author dingshuyan    
	 * @since JDK 1.8
	 */
	@Deprecated
	/*@Scheduled(cron = "0 0/1 * * * ?")*/
	public void refreshMlessonStatus() {
		logger.debug("begin mlesson schedule...");
		List<MLesson> mLessonList = mLessonBiz.selectEndMlesson();
		for(MLesson mLesson : mLessonList){
			mLessonBiz.updateStatus(mLesson.getId(), -1);
			MCourse mCourse = igrowthMCourseBiz.findByChildIdAndCourseId(mLesson.getChildId(), mLesson.getCourseId());
			if(mCourse!=null){
				mCourse.setRemainTimes(mCourse.getRemainTimes()-1);
				igrowthMCourseBiz.updateSelectiveById(mCourse);
			}
		}
		logger.debug("end mlesson schedule...");
	}
	
	/**
	 *  每隔一分钟查询 我的课程卡状态
	 */
	public void refreshMCourseStatus() {
		logger.debug("begin mcourse schedule...");
		MCourse mCourse = new MCourse();
		mCourse.setCourseStatus(0);
		List<MCourse> mcCourses =  igrowthMCourseBiz.selectByExample(mCourse);
		for(MCourse m : mcCourses){
			if(m.getEndTime().before(new Date())){
				m.setCourseStatus(1);
				igrowthMCourseBiz.updateSelectiveById(m);
			}
		}
		logger.debug("end mcourse schedule...");
	}
	
	/**
	 *  每隔五分钟检查机构二维码为空
	 * @author dingshuyan    
	 * @since JDK 1.8
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void refreshOrgQrcodeUrl() {
		logger.debug("begin org qrcode url schedule...");
		List<Org> orgList = orgBiz.selectQrCodeUrlEmpty();
		for (Org org : orgList) {
			try {
				File file = MatrixToImageWriter.createQrcodeFile(String.valueOf(org.getId()));
				String imgUrl = mediaAdminBiz.uploadToOss(file, MediaType.IMAGE_JPEG_VALUE);
				org.setOrgQrcodeUrl(imgUrl);
				orgBiz.updateById(org);
				try {
					Thread.currentThread().sleep(5000);
					file.delete();
				} catch (Exception e) {
					file.delete();
				}
			} catch (Exception e) {

			}
		}
		logger.debug("end org qrcode url schedule...");
	}
	
	
	/**
	 *  每隔五分钟刷新机构信息
	 * @author dingshuyan    
	 * @since JDK 1.8
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void refreshOrgCache() {
		logger.debug("begin org info schedule...");
		ListOperations<String, Org> list = redisTemplate.opsForList();
		redisTemplate.opsForValue().getOperations().delete("orgList");
		List<Org> orgList = orgBiz.selectAll();
		Map<Long, Org> map = new HashMap<Long, Org>();
		for (Org org : orgList) {
			if(org.getLatitude()!=null&&org.getLongitude()!=null){
				org.setGeoHash(GeoHashKit.encode(org.getLatitude(), org.getLongitude()).substring(0, 4));
				list.rightPush("orgList", org);
				map.put(org.getId(), org);
			}
		}
		redisTemplate.opsForHash().putAll("orgMap",map);  
		logger.debug("end org info schedule...");
	}
}
