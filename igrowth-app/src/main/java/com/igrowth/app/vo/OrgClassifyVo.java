package com.igrowth.app.vo;

import java.util.ArrayList;
import java.util.List;

import com.github.wxiaoqi.security.api.entity.OrgClassify;

public class OrgClassifyVo {

	private Long id;

	private String classifyName;

	private List<OrgClassifyVo> list;

	public OrgClassifyVo(OrgClassify orgClassify) {
		this.id = orgClassify.getId();
		this.classifyName = orgClassify.getClassifyName();
		this.list=new ArrayList<OrgClassifyVo>();
	}

	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return classify_name
	 */
	public String getClassifyName() {
		return classifyName;
	}

	/**
	 * @param classifyName
	 */
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public List<OrgClassifyVo> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<OrgClassifyVo> list) {
		this.list = list;
	}

	public OrgClassifyVo(Long id, String classifyName, List<OrgClassifyVo> list) {
		this.id = id;
		this.classifyName = classifyName;
		this.list = list;
	}

	@Override
	public String toString() {
		return "OrgClassifyVo{" +
				"id=" + id +
				", classifyName='" + classifyName + '\'' +
				", list=" + list +
				'}';
	}
}