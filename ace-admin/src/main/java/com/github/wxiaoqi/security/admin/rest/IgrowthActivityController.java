package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.admin.entity.ActivityBiz;
import com.github.wxiaoqi.security.api.entity.Activity;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("igrowthActivity")
public class IgrowthActivityController extends BaseController<ActivityBiz,Activity> {
		
	@Autowired
	private ActivityBiz activityBiz;
	
	@Autowired
	private OrgBiz orgBiz;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Activity> list(@RequestParam Map<String, Object> params){
     	Map<String,Object> resultMap = new HashMap<String,Object>();
        Query query = new Query(params);
        String activityShort = null;
        if(params.get("activityShort")!=null){
            activityShort = params.get("activityShort").toString();
        }
        String userId = getCurrentUserId();
		Org org = new Org();
        org.setBaseUserId(Integer.parseInt(userId));
        org = orgBiz.selectOne(org);
        resultMap = activityBiz.findByOrgId(query,org.getId(),activityShort);
        return new TableResultResponse<Activity>((Long)resultMap.get("total"),(List<Activity>)resultMap.get("data"));
    }
    
    @RequestMapping(value = "/activity/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Activity> update(@RequestBody Activity activity){
        try {
			activity.setStartTime(DateUtil.formateStringToDate(activity.getStartTimeStr(), DateUtil.DATE_FROMAT_PATTERN_ONE));
			activity.setEndTime(DateUtil.formateStringToDate(activity.getEndTimeStr(), DateUtil.DATE_FROMAT_PATTERN_ONE));
		} catch (ParseException e) {
		}
    	activityBiz.updateSelectiveById(activity);
        return new ObjectRestResponse<Activity>().rel(true);
    }

    @RequestMapping(value = "/activity",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Activity> add(@RequestBody Activity activity) {
    	String userId = getCurrentUserId();
		Org org = new Org();
        org.setBaseUserId(Integer.parseInt(userId));
        org = orgBiz.selectOne(org);
        activity.setOrgId(org.getId());
        activity.setCreateTime(new Date());
        activity.setActivityStatus(0);
        try {
			activity.setStartTime(DateUtil.formateStringToDate(activity.getStartTimeStr(), DateUtil.DATE_FROMAT_PATTERN_ONE));
			activity.setEndTime(DateUtil.formateStringToDate(activity.getEndTimeStr(), DateUtil.DATE_FROMAT_PATTERN_ONE));
		} catch (ParseException e) {
		}
        baseBiz.insertSelective(activity);
        return new ObjectRestResponse<Activity>().rel(true);
    }
}