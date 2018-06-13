package com.igrowth.app.rest;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.api.entity.MyCircle;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.CircleBiz;
import com.igrowth.app.biz.MyCircleBiz;
import com.igrowth.app.vo.MyCircleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: CircleController <br/>  
 * Function: 兴趣圈子api类. <br/>  
 * date: 2017年10月20日 上午11:43:38 <br/>  
 *  
 * @author lpf  
 * @version   
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/circle")
public class CircleController extends BaseController {
	
	@Autowired
	private CircleBiz circleBiz;

	@Autowired
	private MyCircleBiz myCircleBiz;

	/**
	 * 我的兴趣圈
	 * findCircleByPageForMe:(根据分页条件查询圈子列表). <br/>
	 *
	 * @author lpf
	 * @param limit
	 * @param page
	 * @return  TableResultResponse<Circle>
	 * @since JDK 1.8
	 */
	@GetMapping("/circles/me")
	public List<MyCircleVO> findCircleByPageForMe(@RequestParam(defaultValue = "10") int limit,
															  @RequestParam(defaultValue = "1") int page,
															  @RequestParam(defaultValue = "id") String sortColumn,
															  @RequestParam(defaultValue = "desc") String orderBy) {
		Long childId = getCurrentChildId();
		if(childId!=null){
			List<MyCircleVO> result = myCircleBiz.findCircleByChildId(childId);
			return result;
		}else{
			return new ArrayList<MyCircleVO>();
		}
	}

	/**
	 * 关注/取消兴趣圈
	 * 0  关注
	 * 1  取消关注
	 * @param circleId
	 * @return
	 */
	@PostMapping("/add/favCircle/{circleId}")
	public Map<String,Object> addMyFavCircle(@PathVariable Long circleId){
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		try{
			MyCircle myCircle = new MyCircle();
			myCircle.setChildId(getCurrentChildId());
			myCircle.setCircleId(circleId);
			MyCircle c = myCircleBiz.selectOne(myCircle);
			if (c != null) {
				if (0 == c.getIsfocus()) {
					c.setIsfocus(1);
				} else {
					c.setIsfocus(0);
				}
				myCircleBiz.updateById(c);
			} else {
				myCircle.setIsfocus(0);
				myCircleBiz.insertSelective(myCircle);
			}
			isSuccess = Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
			isSuccess = Boolean.FALSE;
		}
		result.put("isSuccess",isSuccess);
		return result;

	}

	/**
	 * 你感兴趣的
	 * findCircleByPage:(根据分页条件查询圈子列表). <br/>  
	 *  
	 * @author lpf  
	 * @param limit
	 * @param page
	 * @return  TableResultResponse<Circle>
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@GetMapping("/circles/you")
	public TableResultResponse<Circle> findCircleByPageForYou(@RequestParam(defaultValue = "10") int limit,
													    @RequestParam(defaultValue = "1") int page,
													    @RequestParam(defaultValue = "id") String sortColumn,
													    @RequestParam(defaultValue = "asc") String orderBy) {
		Page<Circle> result = circleBiz.findCircleByPage(limit, page,sortColumn,orderBy);
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

	@IgnoreClientToken
	@GetMapping("/circles/{id}")
	public Circle findCircleById(@PathVariable Long id) {
		return circleBiz.selectById(id);
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
	@PutMapping("/circles")
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
