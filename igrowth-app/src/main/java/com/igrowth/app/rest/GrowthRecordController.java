package com.igrowth.app.rest;

import com.github.wxiaoqi.security.api.entity.GrowthRecord;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.igrowth.app.biz.GrowthRecordBiz;
import com.igrowth.app.vo.GrowthRecordVO;
import com.igrowth.app.vo.MarkerInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/2116:51
 *    desc    : 输入描述
 * </pre>
 */
@RestController
@RequestMapping("/record")
public class GrowthRecordController extends BaseController{

    @Autowired
    private GrowthRecordBiz growthRecordBiz;

    /**
     * 根据日期和孩子Id查询该星期的课程信息
     * @param date
     * @return
     */
    @GetMapping("/records")
    public GrowthRecordVO findGrowthRecordByToday(@RequestParam(defaultValue = "") String dateString) {
        GrowthRecordVO result = new GrowthRecordVO();
        Long childId = getCurrentChildId();
        if(StringUtils.isEmpty(dateString)){
            result = growthRecordBiz.findGrowthRecordByDate(childId, new Date());
        }else{
            try {
                Date d = DateUtil.formateStringToDate(dateString, DateUtil.DATE_FROMAT_PATTERN_ONE);
                result = growthRecordBiz.findGrowthRecordByDate(childId,d);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 添加里程碑
     * @param growthRecord
     * @return
     */
    @PostMapping("/records")
    public Map<String,Object> createArticle(@RequestBody GrowthRecord growthRecord) {
        Map<String,Object> result = new HashMap<String,Object>();
        Long childId = getCurrentChildId();
        Boolean isSuccess = Boolean.FALSE;
        if(growthRecord.getId()!=null){
            growthRecord.setId(null);
        }
        growthRecord.setRedirectUrl("2");
        growthRecord.setChildId(childId);
        growthRecord.setPointType(1);//里程碑点
        try {
            growthRecordBiz.insertSelective(growthRecord);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 查看里程碑信息（三个维度：孩子Id，课程Id，日期String[ps:2017-11-22]）
     * @param courseId
     * @param dateString
     * @return
     */
    @GetMapping("/markerinfo/{courseId}")
    public MarkerInfoVO findMarkinfoByPath(@PathVariable Long courseId,
                                           @RequestParam String dateString) {
        MarkerInfoVO result = new MarkerInfoVO();
        Long childId = getCurrentChildId();
        try{
            Date date = DateUtil.formateStringToDate(dateString,DateUtil.DATE_FROMAT_PATTERN_ONE);
            result = growthRecordBiz.findMarkinfoByParam(childId,courseId,date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
