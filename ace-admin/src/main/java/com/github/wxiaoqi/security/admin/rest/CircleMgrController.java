package com.github.wxiaoqi.security.admin.rest;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.admin.biz.CircleMgrBiz;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/610:00
 *    desc    : 输入描述
 * </pre>
 */
@RestController
@RequestMapping("/circle")
public class CircleMgrController extends BaseController<CircleMgrBiz, Circle> {

    @Autowired
    private CircleMgrBiz circleBiz;

    /**
     *
     * findCircleByPage:(根据分页条件查询圈子列表). <br/>
     *
     * @author lpf
     * @param limit
     * @param page
     * @return  TableResultResponse<Circle>
     * @since JDK 1.8
     */
    @GetMapping("/circles")
    public TableResultResponse<Circle> findCircleByPage(@RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "id") String sortColumn,
                                                        @RequestParam(defaultValue = "desc") String orderBy,
                                                        @RequestParam(defaultValue = "") String circleName) {
        Page<Circle> result = circleBiz.findCircleByPage(limit, page,sortColumn,orderBy,circleName);
        return new TableResultResponse<Circle>(result.getTotal(), result.getResult());
    }

    /**
     *
     * findCircleById:(根据Id查询圈子实体). <br/>
     *
     * @author lpf
     * @param id
     * @return
     * @since JDK 1.8
     */
    @GetMapping("/circles/{id}")
    public ObjectRestResponse<Circle> findCircleById(@PathVariable Long id) {
        return new ObjectRestResponse<Circle>().rel(true).data(circleBiz.selectById(id));
    }

    /**
     *
     * createCircle:(新增圈子对象). <br/>
     *
     * @author lpf
     * @param circle
     * @return
     * @since JDK 1.8
     */
    @PostMapping("/circles")
    public Map<String,Object> createCircle(@RequestBody Circle circle) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        if(circle.getId()==null){
            circle.setId(null);
        }
        try {
            circleBiz.insertSelective(circle);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     *
     * updateCircle:(根据Id更新圈子实体). <br/>
     *
     * @author lpf
     * @param circle
     * @return
     * @since JDK 1.8
     */
    @PutMapping("/circles/{id}")
    public Map<String,Object> updateCircle(@RequestBody Circle circle) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        if(circle.getId()==null){
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "更新失败，id不能为空！");
            return result;
        }
        try {
            circleBiz.updateById(circle);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }
        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     *
     * deleteCircle:(删除圈子对象). <br/>
     *
     * @author lpf
     * @param id
     * @return
     * @since JDK 1.8
     */
    @DeleteMapping("/circles/{id}")
    public Map<String,Object> deleteCircle(@PathVariable Long id) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        try {
            circleBiz.deleteById(id);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            e.printStackTrace();

        }

        result.put("isSuccess", isSuccess);
        return result;
    }
}
