package com.igrowth.app.biz;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.ChildMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * ClassName: ChildBiz <br/>  
 * Function: 孩子服务类 <br/>  
 * date: 2017年10月26日 下午6:17:14 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class ChildBiz extends BaseBiz<ChildMapper, Child> {
	
	@Autowired
	private AccountBiz accountBiz;
	
	@Autowired
	private HeartBiz heartBiz;

	public Page<Child> findChildByAccountId(int limit, int page, Long accountId, Long curChildId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", accountId);
		Page<Child> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Child> list = selectByExample(example,page,result);
		for (Child c : list) {
			if (c.getId() == curChildId) {
				c.setCurChild(true);
			} else {
				c.setCurChild(false);
			}
		}
		return result;
	}

	public void createChild(Long accountId, String childName, Integer childSex, String childBrith, String childPic, String childNickname) {
		Child child = new Child();
		child.setParentId(accountId);
		try {
			child.setChildBirth(DateUtil.formateStringToDate(childBrith,DateUtil.DATE_FROMAT_PATTERN_ONE));
		} catch (ParseException e) {
			child.setChildBirth(new Date());
		}
		child.setChildName(childName);
		child.setChildSex(childSex);
		child.setChildPic(childPic);
		child.setChildNickname(childNickname);
		insert(child);
	}
	
	public List<Child> findChildByAccountId(Long accountId){
		return mapper.findChildByAccountId(accountId);
	}
	
	public Child getChildById(Long childId){
		Integer heartNum = heartBiz.findHeartByChildId(childId);
		if (heartNum == null) {
			heartNum = 0;
		}
		Child child =  selectById(childId);
		child.setHeartNum(heartNum);
		return child;
	}
	
	public void saveChild(Child child){
		mapper.insert(child);
	}

	public void updateChild(Long id, String childName, Integer childSex, String childBrith, String childPic, String childNickname) {
		Child child = getChildById(id);
		if(!"".equals(childName)){
			child.setChildName(childName);
		}
		if(-1!=childSex){
			child.setChildSex(childSex);
		}
		if(!"".equals(childBrith)){
			try {
				child.setChildBirth(DateUtil.formateStringToDate(childBrith,DateUtil.DATE_FROMAT_PATTERN_ONE));
			} catch (ParseException e) {
				child.setChildBirth(new Date());
			}
		}
		if(!"".equals(childPic)){
			child.setChildPic(childPic);
		}
		if(!"".equals(childNickname)){
			child.setChildNickname(childNickname);
		}
		mapper.updateByPrimaryKey(child);
	}

	public void selectChild(Long id, Long currentAccountId) {
		Account account = accountBiz.findAccountById(currentAccountId);
		account.setCurrentCid(id);
		accountBiz.updateSelectiveById(account);
	}
}
