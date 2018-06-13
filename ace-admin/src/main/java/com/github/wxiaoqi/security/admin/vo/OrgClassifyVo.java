package com.github.wxiaoqi.security.admin.vo;

import java.util.List;

import com.github.wxiaoqi.security.api.entity.OrgClassify;

public class OrgClassifyVo {

	private Long value;

	private String label;

	private List<OrgClassifyVo> children;

	/**
	 * value.
	 * 
	 * @return the value
	 * @since JDK 1.8
	 */
	public Long getValue() {
		return value;
	}

	/**
	 * value.
	 * 
	 * @param value
	 *            the value to set
	 * @since JDK 1.8
	 */
	public void setValue(Long value) {
		this.value = value;
	}

	/**  
	 * label.  
	 *  
	 * @return  the label  
	 * @since   JDK 1.8  
	 */
	public String getLabel() {
		return label;
	}

	/**  
	 * label.  
	 *  
	 * @param   label    the label to set  
	 * @since   JDK 1.8  
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * children.
	 * 
	 * @return the children
	 * @since JDK 1.8
	 */
	public List<OrgClassifyVo> getChildren() {
		return children;
	}

	/**
	 * children.
	 * 
	 * @param children
	 *            the children to set
	 * @since JDK 1.8
	 */
	public void setChildren(List<OrgClassifyVo> children) {
		this.children = children;
	}

	public OrgClassifyVo(OrgClassify orgClassify) {
		this.value = orgClassify.getId();
		this.label = orgClassify.getClassifyName();
	}

	public OrgClassifyVo(Long value, String label, List<OrgClassifyVo> children) {
		this.value = value;
		this.label = label;
		this.children = children;
	}
}