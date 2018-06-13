package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "igrowth_org_classify")
public class OrgClassify {
    @Id
    private Long id;

    @Column(name = "classify_name")
    private String classifyName;
    
    /**
     * 分类类型 0机构 1课程表
     */
    @Column(name = "classify_type")
    private Integer classifyType;

    private Long pid;

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

    /**
     * @return pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

	/**  
	 */
	public Integer getClassifyType() {
		return classifyType;
	}

	/**  
	 */
	public void setClassifyType(Integer classifyType) {
		this.classifyType = classifyType;
	}

    @Override
    public String toString() {
        return "OrgClassify{" +
                "id=" + id +
                ", classifyName='" + classifyName + '\'' +
                ", classifyType=" + classifyType +
                ", pid=" + pid +
                '}';
    }
}