package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.MyCircle;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.MyCircleMapper;
import com.igrowth.app.vo.MyCircleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * ClassName: CircleBiz <br/>  
 * Function: 我的圈子服务类. <br/>
 * date: 2017年10月20日 上午11:59:11 <br/>  
 *  
 * @author lpf  
 * @version   
 * @since JDK 1.8
 */
@Service
public class MyCircleBiz extends BaseBiz<MyCircleMapper, MyCircle> {
	
	@Autowired
	private MyCircleMapper myCircleMapper;

	/**
	 * 查询我关注的兴趣圈
	 * @param childId
	 * @return
	 */
	public List<MyCircleVO> findCircleByChildId(Long childId) {
			return myCircleMapper.findMyCircleByChildId(childId);
	}
}
