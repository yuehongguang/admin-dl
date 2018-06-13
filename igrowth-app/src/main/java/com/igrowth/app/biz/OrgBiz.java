package com.igrowth.app.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Activity;
import com.github.wxiaoqi.security.api.entity.Banner;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.entity.OrgClassify;
import com.github.wxiaoqi.security.api.util.GeoHashKit;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.OrgMapper;
import com.igrowth.app.utils.LocationUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 机构服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class OrgBiz extends BaseBiz<OrgMapper, Org> {

	@Autowired
	private CourseBiz courseBiz;
	
	@Autowired
	private ActivityBiz activityBiz;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private OrgClassifyBiz orgClassifyBiz;
	
	/**
	 * getOrgById:id查询. <br/>
	 */
	public Org getOrgVoById(Long id) {
		Org org = (Org) redisTemplate.opsForHash().get("orgMap", id);  
		List<Course> courses = courseBiz.selectCourseByOrgId(id);
		List<Activity> activitys = activityBiz.selectActivityByOrgId(id);
		org.setCourse(courses);
		if(StringUtils.isNotEmpty(org.getOrgLable())){
			org.setCourseLables(getOrgCourseLablesReids(org));
		}else{
			org.setCourseLables(new String[]{});
		}
		String [] pics = new String[]{};
		if(org.getOrgPic()!=null&&!org.getOrgPic().isEmpty()){
			pics = org.getOrgPic().split(",");
		}
		List<Banner> list = new ArrayList<Banner>();
		for(int i=0;i<pics.length;i++){
			Banner banner = new Banner();
			banner.setId(Long.valueOf(i+1));
  			banner.setImgurl(pics[i]);
			list.add(banner);
		}
		org.setOrgPics(list);
		org.setActivities(activitys);
		return org;
	}

	/**
	 * getOrgById:id查询. <br/>
	 */
	public Org getOrgById(Long id) {
		return selectById(id);
	}

	/**
	 * findOrgByPage:分页查询.<br/>
	 */
	public Page<Org> findOnLineOrgByPage(int limit, int page, String keyword, Long orgClassifyId, double latitude,
										 double longitude, double distance, String label) {
		boolean needGeoHash = StringUtils.isNotEmpty(keyword)?false:true;
		String userGeoHash = "";
		if(latitude==0&&longitude==0){
			userGeoHash = GeoHashKit.encode(39.84038,116.28804).substring(0, 4);

		}else{
			userGeoHash = GeoHashKit.encode(latitude, longitude).substring(0, 4);

		}
		List<Org> orgList = getOrgList();

		List<Org> orgListTemp = new ArrayList<Org>();
		Page<Org> result = PageHelper.startPage(1, 100);

		for(Org o : orgList){

			if(StringUtils.isNotEmpty(keyword)){
				if(!o.getOrgName().contains(keyword)){
					continue;
				}
			}

			if(StringUtils.isNotEmpty(label)){
				if(o.getOrgLable()==null||!o.getOrgLable().contains(label)){
					continue;
				}
			}

			if(o.getOrgType()!=0){
				continue;
			}

			if(needGeoHash&&!userGeoHash.equals(o.getGeoHash())){
				continue;
			}

			orgListTemp.add(o);
		}
		System.out.println("=========================================="+orgListTemp);
		orgList.clear();
		orgList = orgListTemp;
		List<Org> rList = new ArrayList<Org>();
		/** 查询机构距离 **/
		for (Org org : orgList) {
			if(org.getLatitude()!=null&&org.getLongitude()!=null){
				double d = LocationUtils.getDistance(org.getLatitude(), org.getLongitude(), latitude, longitude);
				if (distance != 0.0d&& distance<d) {
				}else{
					rList.add(org);
				}
				if(StringUtils.isNotEmpty(org.getOrgLable())){
					org.setCourseLables(getOrgCourseLablesReids(org));
				}else{
					org.setCourseLables(new String[]{});
				}
				org.setDistance(d);
			}else{
				org.setDistance(99999999D);
				rList.add(org);
			}

		}
		List<Org> rList2 = new ArrayList<Org>();
		if (orgClassifyId != 0) {
			for (Org org : orgList) {
				if (StringUtils.isNotEmpty(org.getOrgClassifyId())) {
					if (Arrays.asList(org.getOrgClassifyId().split(",")).contains(String.valueOf(orgClassifyId))) {
						if(!rList2.contains(org)){
							rList2.add(org);
						}
					}	
				}
			}
			rList.clear();
			rList.addAll(rList2);
		}
		Collections.sort(rList);
		orgList.clear();
		int end = 0;
		int start = 0;
		if(page*limit>=rList.size()){
			end = rList.size();
		}else{
			end = page*limit;
		}
		if((page-1)*limit>=rList.size()){
			start = rList.size();
		}else{
			start = (page-1)*limit;
		}
		result.setTotal(rList.size());
		rList = rList.subList(start, end);
		result.addAll(rList);
		return result;
	}
	
	public Page<Org> findOffLineOrgByPage(int limit, int page, String keyword) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(keyword)) {
			criteria.andLike("orgName", "%" + keyword + "%");
		}
		criteria.andEqualTo("orgType", 1);
		Page<Org> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;
	}
	
	@Deprecated
	public String [] getOrgCourseLables(Long orgId){
		List<String> list =  mapper.getOrgCourseLables(orgId);
		return list.toArray(new String[list.size()]);
	}

	public List<Org> getOrgList() {
		ListOperations<String, Org> list = redisTemplate.opsForList();
		if (list.size("orgList") == 0) {
			List<Org> orgList = mapper.selectAll();
			for (Org org : orgList) {
				if(org.getLatitude()!=null&&org.getLongitude()!=null){
					org.setGeoHash(GeoHashKit.encode(org.getLatitude(), org.getLongitude()).substring(0, 4));
				}
			}
			return orgList;
		}else{
			return list.range("orgList", 0, list.size("orgList") - 1);
		}
	}
	
	public String[] getOrgCourseLablesReids(Org org) {
		if (org.getOrgClassifyId()==null||org.getOrgClassifyId().isEmpty()) {
			return new String[] {};
		}
		Map<Long, String> orgClassifyMap = new HashMap<Long, String>();
		if (redisTemplate.opsForHash().size("orgClassifyMap") == 0) {
			List<OrgClassify> orgClassifyList = orgClassifyBiz.selectAll();
			for (OrgClassify orgClassify : orgClassifyList) {
				orgClassifyMap.put(orgClassify.getId(), orgClassify.getClassifyName());
			}
			redisTemplate.opsForHash().putAll("orgClassifyMap", orgClassifyMap);
		}
		orgClassifyMap = redisTemplate.opsForHash().entries("orgClassifyMap");
		String[] orgClassifyIds = org.getOrgClassifyId().split(",");
		String[] courseLables = new String[orgClassifyIds.length];
		for (int i = 0; i < orgClassifyIds.length; i++) {
			if(StringUtils.isNotEmpty(orgClassifyIds[i])) {
				courseLables[i] = orgClassifyMap.get(Long.valueOf(orgClassifyIds[i]));
			}
		}
		return courseLables;
	}
}
