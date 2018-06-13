package com.github.wxiaoqi.security.admin.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxiaoqi.security.admin.biz.CourseBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.admin.biz.OrgTeacherRelationBiz;
import com.github.wxiaoqi.security.admin.biz.TeacherBiz;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.entity.OrgTeacherRelation;
import com.github.wxiaoqi.security.api.entity.Teacher;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;

@RestController
@RequestMapping("igrowthTeacher")
public class IgrowthTeacherController extends BaseController<TeacherBiz,Teacher> {

    @Autowired
    private TeacherBiz teacherBiz;
    
    @Autowired
    private OrgBiz orgBiz;
    
    @Autowired
    private OrgTeacherRelationBiz orgTeacherRelationBiz;
    
    @Autowired
    private CourseBiz courseBiz;

    /**
     * findTeacherByPage(根据分页条件查询老师列表)
     * @param limit
     * @param page	
     * @param sortColumn
     * @param orderBy
     * @return
     */
    @GetMapping("/teachers/page")
    public TableResultResponse<Teacher> findTeacherByPage(@RequestParam(defaultValue = "10") int limit,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "id") String sortColumn,
                                                          @RequestParam(defaultValue = "desc") String orderBy,
                                                          @RequestParam(defaultValue = "") String namePhone) {
    	
    	String userId = getCurrentUserId();
    	Map<String,Object> resultMap = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(userId)) {
			Org org = new Org();
            org.setBaseUserId(Integer.parseInt(userId));
            org = orgBiz.selectOne(org);
			resultMap = teacherBiz.findTeacherByOrgIdAndPage(org.getId(), limit, page,namePhone);
		}
        return new TableResultResponse<Teacher>((Long)resultMap.get("total"),(List<Teacher>)resultMap.get("data"));
    }
    
    
    /**
     * findOrgByPage(根据分页条件查询机构邀请列表)
     * @param limit
     * @param page	
     * @param sortColumn
     * @param orderBy
     * @return
     */
    @GetMapping("/orgs/page")
    public TableResultResponse<Org> findOrgByPage(@RequestParam(defaultValue = "10") int limit,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "id") String sortColumn,
                                                          @RequestParam(defaultValue = "desc") String orderBy) {
    	
    	String userId = getCurrentUserId();
    	Map<String,Object> resultMap = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(userId)) {
			Teacher teacher = new Teacher();
			teacher.setUserId(Long.valueOf(userId));
			teacher = teacherBiz.selectOne(teacher);
			resultMap = orgBiz.findOrgByTeacherIdAndPage(teacher.getId(), limit, page);
		}
        return new TableResultResponse<Org>((Long)resultMap.get("total"),(List<Org>)resultMap.get("data"));
    }

    /**
     * 查询该机构下的所有老师
     * @return
     */
    @GetMapping("/teachers/byorg")
    public List<Teacher> findTeacherByOrg() {

        String userId = getCurrentUserId();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if (StringUtils.isNotEmpty(userId)) {
            Org org = new Org();
            org.setBaseUserId(Integer.parseInt(userId));
            org = orgBiz.selectOne(org);
            return teacherBiz.findTeacherByOrgId(org.getId());
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * 根据id查询教师
     * @param id
     * @return
     */
    @GetMapping("/teachers/{id}")
    public Teacher findTeacherById(@PathVariable Long id) {
        return teacherBiz.findTeacherById(id);
    }
    
    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    @GetMapping("/courses/{id}")
    public List<Course> findCourseByTeacherId(@PathVariable Long id) {
        String userId = getCurrentUserId();
        Org org = new Org();
        org.setBaseUserId(Integer.valueOf(userId));
        org = orgBiz.selectOne(org);
    	List<Course> list = courseBiz.selectByTeacherIdAndOrgId(id,org.getId());
    	return list;
    }

    /**
     * 根据教师手机号查询教师
     * @param phone
     * @return
     */
    @GetMapping("/teachers/phone")
    public ObjectRestResponse<Teacher> findTeacherByPhobe(@RequestParam(defaultValue = "") String phone) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone",phone);
        Query query = new Query(params);
        return new ObjectRestResponse<Teacher>().rel(true).data(teacherBiz.selectByQuery(query));
    }

    /**
     * 保存老师
     * @param teacher
     * @return
     */
    @PostMapping("/teachers")
    public Map<String,Object> createCard(@RequestBody Teacher teacher) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        try {
           Teacher t = teacherBiz.findTeacherByPhone(teacher.getPhone());
	       Org org = new Org();
	       org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
	       org = orgBiz.selectOne(org);
	       OrgTeacherRelation orgTeacherRelation =  orgTeacherRelationBiz.findByTeacherIdAndOrgId(Long.valueOf(t.getId()),org.getId());
	       if(orgTeacherRelation==null){
	    	   orgTeacherRelationBiz.createOrgTeacherRelation(org.getId(), Long.valueOf(t.getId()));
	       }
	       isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();
        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 更新教师
     * @param teacher
     * @return
     */
    @PutMapping("/teachers/{id}")
    public Map<String,Object> updateCard(@RequestBody Teacher teacher) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        if(teacher.getId()==null){
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "更新失败，id不能为空！");
            return result;
        }
        try {
            teacherBiz.updateById(teacher);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 删除教师
     * @param id
     * @return
     */
    @DeleteMapping("/teachers/{id}")
    public Map<String,Object> deleteCard(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isSuccess = Boolean.FALSE;
		try {
			Org org = new Org();
			org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
			org = orgBiz.selectOne(org);
			OrgTeacherRelation orgTeacherRelation = orgTeacherRelationBiz.findByTeacherIdAndOrgId(id, org.getId());
			List<Course> courseList = courseBiz.selectByTeacherIdAndOrgId(orgTeacherRelation.getTeacherId(),orgTeacherRelation.getOrgId());
			if (courseList != null && !courseList.isEmpty()) {
				isSuccess = Boolean.FALSE;
			} else {
				orgTeacherRelationBiz.delete(orgTeacherRelation);
			}
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();
		}
		result.put("isSuccess", isSuccess);
		return result;
    }
    
    /**
     * 同意机构添加教师邀请
     * @param id
     * @return
     */
    @GetMapping("/org/agree/{id}")
    public Map<String,Object> agree(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isSuccess = Boolean.FALSE;
		try {
			OrgTeacherRelation otr = orgTeacherRelationBiz.selectById(id);
			otr.setoTStatus(1);
			orgTeacherRelationBiz.updateSelectiveById(otr);
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();
		}
		result.put("isSuccess", isSuccess);
		return result;
    }
    
    /**
     * 拒绝机构添加教师邀请
     * @param id
     * @return
     */
    @GetMapping("/org/refuse/{id}")
    public Map<String,Object> refuse(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isSuccess = Boolean.FALSE;
		try {
			OrgTeacherRelation otr = orgTeacherRelationBiz.selectById(id);
			otr.setoTStatus(-1);
			orgTeacherRelationBiz.updateSelectiveById(otr);
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();
		}
		result.put("isSuccess", isSuccess);
		return result;
    }

}