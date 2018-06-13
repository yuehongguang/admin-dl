package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.*;
import com.github.wxiaoqi.security.admin.vo.IgrowthMCourseVO;
import com.github.wxiaoqi.security.api.entity.*;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("igrowthMCourse")
public class IgrowthMCourseController extends BaseController<IgrowthMCourseBiz,MCourse> {

    @Autowired
    private IgrowthMCourseBiz igrowthMCourseBiz;

    @Autowired
    private CourseBiz courseBiz;
    
    @Autowired
    private ChildBiz childBiz;
    
    @Autowired
    private MLessonBiz mLessonBiz;
    
    @Autowired
    private OrgBiz orgBiz;
    
    @Autowired
    private AccountBiz accountBiz;
 
    
    /**
     * 根据当前机构查询客户信息
     * @param limit
     * @param page
     * @param sortColumn
     * @param orderBy
     * @return
     */
    @GetMapping("/courses")
    public TableResultResponse<IgrowthMCourseVO> findCardByPage(@RequestParam(defaultValue = "10") int limit,
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "0") int childId,
                                                                @RequestParam(defaultValue = "") String namePhone) {
    	
        Org org = new Org();
        org.setBaseUserId(Integer.valueOf(getCurrentUserId()));
        org = orgBiz.selectOne(org);
        if(org!=null){
            Map<String,Object> result = igrowthMCourseBiz.findCourseByPage(limit, page,org.getId(),childId,namePhone);
            Long total = (Long)result.get("total");
            List<IgrowthMCourseVO> list = (List)result.get("data");
            return new TableResultResponse<IgrowthMCourseVO>(total,list);
        }else{
            return new TableResultResponse<IgrowthMCourseVO>();
        }

    }

    @RequestMapping(value = "/courses/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public List<MCourse> getLesson(@PathVariable Long courseId){
        List<MCourse> list = igrowthMCourseBiz.findByCourseId(courseId);
        return list;
    }
    
    /**
     * 根据课程id添加我的课程
     * @param mc
     */
    @PostMapping("/courses/add")
    public Map<String, Object> addMcourseByCourseId(@RequestBody MCourse mc){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
    	if(mc.getChildId()==null){
    		map = accountBiz.register(mc.getParentPhone(), mc.getChildName());
    		if(map.get("account")==null){
    			resultMap.put("result", false);
    			resultMap.put("msg", map.get("msg"));
    			return resultMap;
    		}else{
    			Account account = (Account) map.get("account");
    			mc.setChildId(account.getCurrentCid());
    		}
    	}
    	long cardId = mc.getId();
    	MCourse m = igrowthMCourseBiz.findByChildIdAndCourseCardId(mc.getChildId(),cardId);
    	if(m!=null){
    		resultMap.put("result", false);
    		resultMap.put("msg", "该用户已存在绑定的课程卡,请不要重复绑定.");
    		return resultMap;
    	}
        Course course = new Course();
        course.setCardId(cardId);
        Course c = courseBiz.selectOne(course);

        //创建mlesson
        Child child = childBiz.selectById(mc.getChildId());
        Map<String,Object> result = mLessonBiz.bulidMlesson(c,child);
        if(!(Boolean) result.get("isSuccess")){
            resultMap.put("result",false);
            resultMap.put("msg",result.get("message").toString());
            return resultMap;
        }
        //创建mcourse
        igrowthMCourseBiz.bulidMCourse(mc,c);

        resultMap.put("result", true);
        resultMap.put("msg", "添加成功.");
		return resultMap;
    }

    @RequestMapping(value = "/mcourses/{id}",method = RequestMethod.GET)
    @ResponseBody
    public MCourse getMCourse(@PathVariable long id){
        return igrowthMCourseBiz.selectById(id);
    }

    /**
     * 删除”我的“课程和课表
     * @param id  mcourseId
     * @return
     */
    @PostMapping("/mcourseDelete")
    @Transactional
    public Map<String,Object> deletMcourseMlesson(@RequestBody MCourse mCourse){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
       try{
           MLesson mLesson = new MLesson();
           mLesson.setCourseId(mCourse.getCourseId().longValue());
           mLesson.setChildId(mCourse.getChildId());
           mLesson.setOrgId(mCourse.getOrgId().longValue());
           List<MLesson> mLessonList = mLessonBiz.selectList(mLesson);
           //查找已签到或请假课程
           if(mLessonList.size()>0){
                List<MLesson> filterList1 = mLessonList.stream().filter(a -> a.getLessonStatus().equals(1)).collect(Collectors.toList());
                List<MLesson> filterList2 = mLessonList.stream().filter(a -> a.getLessonStatus().equals(2)).collect(Collectors.toList());
                if(filterList1.size()==0&&filterList2.size()==0){//如果课程没有被签到或请假才允许删除
                    for(MLesson mlesson1 : mLessonList){//删除”我的“课表
                        mLessonBiz.delete(mlesson1);
                    }
                }else{
                    resultMap.put("isSuccess",isSuccess);
                    resultMap.put("message","学员已参与该课程，请勿删除！");
                    return resultMap;
                }
           }
           //删除”我的“课程
           MCourse mCourse1 = new MCourse();
           mCourse1.setChildId(mCourse.getChildId());
           mCourse1.setOrgId(mCourse.getOrgId());
           mCourse1.setCourseId(mCourse.getCourseId());
           igrowthMCourseBiz.delete(mCourse1);
           isSuccess = Boolean.TRUE;
       }catch(Exception e){
           e.printStackTrace();
       }
        resultMap.put("message","删除成功！");
        resultMap.put("isSuccess",isSuccess);
        return resultMap;
    }
}