package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrowth.app.biz.TestBiz;

/**
 * 
 * ClassName: ArticleController <br/>  
 * Function: 动态（文章）api类. <br/>  
 * date: 2017年10月20日 下午1:54:09 <br/>  
 *  
 * @author lpf  
 * @version   
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
	
	@Autowired
	private TestBiz testBiz;

	@GetMapping("/tests")
	public void findArticleByPage() {
		System.out.println("oh   yes！----------------------------");
	}
	
}
