package com.github.wxiaoqi.security.admin.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.OrgTeacherRelationMapper;
import com.github.wxiaoqi.security.api.entity.OrgTeacherRelation;
import com.github.wxiaoqi.security.common.biz.BaseBiz;

/**
 * ClassName: OrgBiz <br/>
 * Function: 机构服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class OrgTeacherRelationBiz extends BaseBiz<OrgTeacherRelationMapper, OrgTeacherRelation> {

	public void createOrgTeacherRelation(Long orgId,Long teacherId){
		OrgTeacherRelation orgTeacherRelation = new OrgTeacherRelation();
		orgTeacherRelation.setOrgId(orgId);
		orgTeacherRelation.setTeacherId(teacherId);
		orgTeacherRelation.setoTStatus(0);
		mapper.insert(orgTeacherRelation);
	}

	public OrgTeacherRelation findByTeacherIdAndOrgId(Long teacherId, Long orgId) {
		OrgTeacherRelation orgTeacherRelation = new OrgTeacherRelation();
		orgTeacherRelation.setTeacherId(teacherId);
		orgTeacherRelation.setOrgId(orgId);
		return mapper.selectOne(orgTeacherRelation);
	}
}
