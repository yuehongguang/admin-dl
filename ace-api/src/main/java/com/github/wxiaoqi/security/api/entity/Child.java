package com.github.wxiaoqi.security.api.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ClassName: Child <br/>  
 * Function: . <br/>  
 * date: 2017年10月26日 下午3:59:04 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Table(name = "igrowth_child")
public class Child {

    public static final String CHILD_PIC_DE = "http://static.chinacnid.com/media/file/b4e8a8de-cc6e-4d14-9c91-7015afd09c86.jpg";
	
    @Id
    private Long id;

    /**
     * 孩子姓名
     */
    @Column(name = "child_name")
    private String childName;
    
    /**
     * 孩子昵称
     */
    @Column(name = "child_nickname")
    private String childNickname;
    
    /**
     * 孩子性别
     */
    @Column(name = "child_sex")
    private Integer childSex;//孩子性别

    /**
     * 孩子出生年月
     */
    @Column(name = "child_birth")
    private Date childBirth;//孩子出生日期

    /**
     * 孩子头像
     */
    @Column(name = "child_pic")
    private String childPic;//孩子图片

    /**
     * 对应accountId
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;
    
    
    @Transient
    private boolean isCurChild;
    
    /**
     * 当前孩子的爱心数量
     */
    @Transient
    private Integer heartNum;

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
     * @return child_name
     */
    public String getChildName() {
        return childName;
    }

    /**
     * @param childName
     */
    public void setChildName(String childName) {
        this.childName = childName;
    }

    /**
     * @return child_sex
     */
    public Integer getChildSex() {
        return childSex;
    }

    /**
     * @param childSex
     */
    public void setChildSex(Integer childSex) {
        this.childSex = childSex;
    }

    /**  
	 * childNickname.  
	 */
	public String getChildNickname() {
		return childNickname;
	}

	/**  
	 * childNickname.  
	 */
	public void setChildNickname(String childNickname) {
		this.childNickname = childNickname;
	}

	/**
     * @return child_birth
     */
    public Date getChildBirth() {
        return childBirth;
    }

    /**
     * @param childBirth
     */
    public void setChildBirth(Date childBirth) {
        this.childBirth = childBirth;
    }

    /**
     * @return child_pic
     */
    public String getChildPic() { return StringUtils.isNotEmpty(childPic)?childPic:CHILD_PIC_DE; }

    /**
     * @param childPic
     */
    public void setChildPic(String childPic) {
        this.childPic = StringUtils.isNotEmpty(childPic)?childPic:CHILD_PIC_DE;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**  
	 * isCurChild.  
	 */
	public boolean isCurChild() {
		return isCurChild;
	}

	/**  
	 * isCurChild.  
	 */
	public void setCurChild(boolean isCurChild) {
		this.isCurChild = isCurChild;
	}

	/**  
	 * heartNum.  
	 */
	public Integer getHeartNum() {
		return heartNum;
	}

	/**  
	 */
	public void setHeartNum(Integer heartNum) {
		this.heartNum = heartNum;
	}
}