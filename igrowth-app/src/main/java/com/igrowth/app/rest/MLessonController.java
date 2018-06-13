package com.igrowth.app.rest;

import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.msg.AppResponse;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.igrowth.app.biz.GrowthRecordBiz;
import com.igrowth.app.biz.MLessonBiz;
import com.igrowth.app.vo.MCourseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: OrgController <br/>  
 * Function: 我的课程api类 <br/>  
 * date: 2017年10月19日 下午6:06:06 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("mlesson")
public class MLessonController extends BaseController {
	
	@Autowired
	private MLessonBiz mLessonBiz;
	
	@Autowired
	private GrowthRecordBiz growthRecordBiz;
	
	/**
	 * 我的课程列表接口
	 * @author dingshuyan  
	 * @param dateStr
	 * @param courseStatus
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/mlessons", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse<Object> page(@RequestParam(defaultValue = "") String dateStr,
								    @RequestParam(defaultValue = "-1") Integer courseStatus) throws ParseException {
		 
		Date date ;
		if("".equals(dateStr)){
			date = new Date();
		}else{
			date = DateUtil.formateStringToDate(dateStr,DateUtil.DATE_FROMAT_PATTERN_ONE);
		}
		Map<String,Date> dateMap = DateUtil.getMondyToSundayByNow(date,DateUtil.DATE_NAME_ONE);
		List<MCourseVO> mList = mLessonBiz.findMLessonByDate(getCurrentChildId(),dateMap,courseStatus);
		return new AppResponse<Object>(200, "success", mList);
	} 
	
	/**
	 * 我的课程列表接口
	 * @author dingshuyan  
	 * @param dateStr
	 * @param courseStatus
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/mlesson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse<Object> page(@PathVariable Long id) {
		MLesson mLesson = mLessonBiz.findById(id);
		return new AppResponse<Object>(200, "success", mLesson);
	}
	

	/**
	 * 扫码签到首页
	 * @param orgId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/signCome/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public List<MLesson> signComeIndex(@PathVariable Integer orgId) throws ParseException {
		return mLessonBiz.findMLessonByParam(orgId,getCurrentChildId(),new Date());
	}

	
	/**
	 * 点击签到
	 * @param orgId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/clickSign", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String,Boolean> signCome(@RequestParam(defaultValue = "") String mLessonIds) throws ParseException {
		Map<String,Boolean> result = new HashMap<String,Boolean>();
		Boolean isSuccess = Boolean.FALSE;
		try{
			if(StringUtils.isNotEmpty(mLessonIds)){
				String[] ids = mLessonIds.split(",");
				for(String id:ids){
					MLesson mLesson = mLessonBiz.sign(Long.valueOf(id),getCurrentAccountId(),getCurrentChildId());
				}
				isSuccess = Boolean.TRUE;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		result.put("isSuccess",isSuccess);
		return result;
	}
	
	/**
	 * 请假
	 * @author dingshuyan  
	 * @param messageType
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/lelesson/{mLessonId}", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse<Object> lelesson(@PathVariable Long mLessonId){
		try{
			mLessonBiz.lelesson(mLessonId,getCurrentAccountId(),getCurrentChildId());
			return new AppResponse<Object>(200, "success", true);
		}catch(Exception e){
			return new AppResponse<Object>(200, "fail", false);
		}
	} 
	
	/**
	 * 签到
	 * @author dingshuyan  
	 * @param messageType
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/sign/{mLessonId}", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse<Object> sign(@PathVariable Long mLessonId){
		try{
			mLessonBiz.sign(mLessonId,getCurrentAccountId(),getCurrentChildId());
			return new AppResponse<Object>(200, "success", true);
		}catch(Exception e){
			return new AppResponse<Object>(200, "fail", false);
		}
	} 

	/**
	 * selflesson:自选课程. <br/>  
	 * @author dingshuyan  
	 * @param courseName
	 * @param lable
	 * @param date
	 * @param tlong
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/selflesson", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> selflesson(@RequestParam String courseName,
										  @RequestParam String lable, 
								          @RequestParam String dateStr,
								          @RequestParam Integer tlong,
								          @RequestParam(defaultValue = "") String note) {
		Map<String, Object> rMap = new HashMap<String, Object>();
		try{
			MLesson mLesson = mLessonBiz.selflesson(getCurrentAccountId(),getCurrentChildId(),courseName,lable,dateStr,tlong,note);
			growthRecordBiz.createRecord(getCurrentChildId(),mLesson,lable,"1");
			rMap.put("result", true);
		}catch(Exception e){
			rMap.put("result", false);
		}
		return rMap;
	}
	
	/**
	 * 删除自选课程
	 * @author dingshuyan  
	 * @param courseName
	 * @param lable
	 * @param date
	 * @param tlong
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dellesson/{mlessonId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delSelfLesson(@PathVariable Long mlessonId) {
		Map<String, Object> rMap = new HashMap<String, Object>();
		try{
			mLessonBiz.deleteById(mlessonId);
			rMap.put("result", true);
		}catch(Exception e){
			rMap.put("result", false);
		}
		return rMap;
	}
	
	/**
	 * 更新自选课程
	 * @author dingshuyan  
	 * @param courseName
	 * @param lable
	 * @param date
	 * @param tlong
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/updateMlesson/{mlessonId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMlesson(@PathVariable Long mlessonId,
										     @RequestParam String courseName,
										     @RequestParam String lable, 
							                 @RequestParam String dateStr,
							                 @RequestParam Integer tlong,
							                 @RequestParam(defaultValue = "") String note) {
		Map<String, Object> rMap = new HashMap<String, Object>();
		try{
			mLessonBiz.updateMlesson(mlessonId,courseName,lable,dateStr,tlong,note);
			rMap.put("result", true);
		}catch(Exception e){
			rMap.put("result", false);
		}
		return rMap;
	}
	
	
}
