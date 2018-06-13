package com.github.wxiaoqi.security.api.vo;

public class ChildVo {
	private Long childId;
	private String childName;
	private String childPic;
	private Long mlessonId;

	/**
	 * childId.
	 * 
	 * @return the childId
	 * @since JDK 1.8
	 */
	public Long getChildId() {
		return childId;
	}

	/**
	 * childId.
	 * 
	 * @param childId
	 *            the childId to set
	 * @since JDK 1.8
	 */
	public void setChildId(Long childId) {
		this.childId = childId;
	}

	/**
	 * childName.
	 * 
	 * @return the childName
	 * @since JDK 1.8
	 */
	public String getChildName() {
		return childName;
	}

	/**
	 * childName.
	 * 
	 * @param childName
	 *            the childName to set
	 * @since JDK 1.8
	 */
	public void setChildName(String childName) {
		this.childName = childName;
	}

	/**  
	 * mlessonId.  
	 *  
	 * @return  the mlessonId  
	 * @since   JDK 1.8  
	 */
	public Long getMlessonId() {
		return mlessonId;
	}

	/**  
	 * mlessonId.  
	 *  
	 * @param   mlessonId    the mlessonId to set  
	 * @since   JDK 1.8  
	 */
	public void setMlessonId(Long mlessonId) {
		this.mlessonId = mlessonId;
	}

	/**  
	 * childPic.  
	 *  
	 * @return  the childPic  
	 * @since   JDK 1.8  
	 */
	public String getChildPic() {
		return childPic;
	}

	/**  
	 * childPic.  
	 *  
	 * @param   childPic    the childPic to set  
	 * @since   JDK 1.8  
	 */
	public void setChildPic(String childPic) {
		this.childPic = childPic;
	}
	
}