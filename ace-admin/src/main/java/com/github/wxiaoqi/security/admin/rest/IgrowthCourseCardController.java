package com.github.wxiaoqi.security.admin.rest;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.admin.biz.CourseBiz;
import com.github.wxiaoqi.security.admin.biz.IgrowthCourseCardBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.admin.entity.OrgClassifyBiz;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("igrowthCourseCard")
public class IgrowthCourseCardController extends BaseController<IgrowthCourseCardBiz,IgrowthCourseCard> {

    @Autowired
    private IgrowthCourseCardBiz igrowthCourseCardBiz;
    @Autowired
    private OrgBiz orgBiz;
    @Autowired
    private OrgClassifyBiz orgClassifyBiz;
    @Autowired
    private CourseBiz courseBiz;
    /**
     *
     * findCircleByPage:(根据分页条件查询课程卡列表). <br/>
     *
     * @author lpf
     * @param limit
     * @param page
     * @return  TableResultResponse<Circle>
     * @since JDK 1.8
     */
    @GetMapping("/cards")
    public TableResultResponse<IgrowthCourseCard> findCardByPage(@RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "") String cardName) {
        Org o = new Org();
        o.setBaseUserId(Integer.parseInt(getCurrentUserId()));
        o = orgBiz.selectOne(o);
        Page<IgrowthCourseCard> result = igrowthCourseCardBiz.findCourseByPage(limit, page,o.getId(),cardName);
        return new TableResultResponse<IgrowthCourseCard>(result.getTotal(), result.getResult());
    }

    /**
     * 查询可用课程卡
     * @return
     */
    @GetMapping("/cards/valid")
    public List<IgrowthCourseCard> findCardByStatus() {
        IgrowthCourseCard card = new IgrowthCourseCard();
        card.setStatus(0);
        card.setBindStatus(0);
        Org o = new Org();
        o.setBaseUserId(Integer.parseInt(getCurrentUserId()));
        o = orgBiz.selectOne(o);
        card.setOrgId(o.getId().intValue());
        return igrowthCourseCardBiz.selectList(card);
    }
    /**
     * 查询课程卡信息
     * @param id
     * @return
     */
    @GetMapping("/cards/{id}")
    public ObjectRestResponse<Circle> findCardById(@PathVariable Long id) {
        IgrowthCourseCard card = igrowthCourseCardBiz.selectById(id);
        return new ObjectRestResponse<Circle>().rel(true).data(card);
    }

    /**
     * 创建课程卡对象
     * @param card
     * @return
     */
    @PostMapping("/cards")
    public Map<String,Object> createCard(@RequestBody IgrowthCourseCard card) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        Org o = new Org();
        o.setBaseUserId(Integer.parseInt(getCurrentUserId()));
        o = orgBiz.selectOne(o);
        card.setBindStatus(0);
        card.setOrgId(o.getId().intValue());
        card.setOrgName(o.getOrgName());
        if(card.getId()!=null){
            card.setId(null);
        }
        try {
            igrowthCourseCardBiz.insertSelective(card);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 更新课程卡对象
     * @param card
     * @return
     */
    @PutMapping("/cards/{id}")
    public Map<String,Object> updateCard(@RequestBody IgrowthCourseCard card) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        if(card.getId()==null){
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "更新失败，id不能为空！");
            return result;
        }
        try {
            igrowthCourseCardBiz.updateById(card);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 删除课程卡对象
     * @param id
     * @return
     */
    @DeleteMapping("/cards/{id}")
    public Map<String,Object> deleteCard(@PathVariable Long id) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        Course course = new Course();
        course.setCardId(id);
        List<Course> courseList = courseBiz.selectList(course);
        if(courseList.size()>0){
            result.put("isSuccess",Boolean.FALSE);
            result.put("message","该课程卡已绑定，无法删除！");
            return result;
        }
        try {
            igrowthCourseCardBiz.deleteById(id);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }

        result.put("isSuccess", isSuccess);
        return result;
    }

    @GetMapping(value="cards/list")
    @ResponseBody
    public List<IgrowthCourseCard> findCourseCard(){
        return igrowthCourseCardBiz.selectListAll();
    }

    /**
     * 查询机构下所有课程卡
     * @return
     */
    @GetMapping(value="cards/org")
    @ResponseBody
    public List<IgrowthCourseCard> findCourseCardByOrg(){
        IgrowthCourseCard card = new IgrowthCourseCard();
        Org o = new Org();
        o.setBaseUserId(Integer.valueOf(getCurrentUserId()));
        o = orgBiz.selectOne(o);
        card.setOrgId(o.getId().intValue());
        return igrowthCourseCardBiz.selectList(card);
    }
}