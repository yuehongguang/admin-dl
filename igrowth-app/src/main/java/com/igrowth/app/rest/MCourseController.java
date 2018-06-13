package com.igrowth.app.rest;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.MCourseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 我的课程卡接口
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("mcourse")
public class MCourseController extends BaseController {

	@Autowired
	private MCourseBiz mCourseBiz;
 
	/**
	 * 课程表接口
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/mcourses/{status}", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<MCourse> page(@RequestParam(defaultValue = "10") int limit,
										     @RequestParam(defaultValue = "1") int page,
										     @PathVariable Integer status) {
		Page<MCourse> result = mCourseBiz.getMcourseByChildId(limit,page,getCurrentChildId(), status);
		return new TableResultResponse<MCourse>(result.getTotal(), result);
	}

	/**
	 * 查询我的课程
	 * @return
	 */
	@RequestMapping(value = "/mcourses/bychild", method = RequestMethod.GET)
	@ResponseBody
	public List<MCourse> getMcourseListByChildId() {
		MCourse m = new MCourse();
		m.setChildId(getCurrentChildId());
		return mCourseBiz.selectList(m);
	}

}
