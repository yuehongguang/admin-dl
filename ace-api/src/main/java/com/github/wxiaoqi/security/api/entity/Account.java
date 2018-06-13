package com.github.wxiaoqi.security.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * ClassName: Account <br/>  
 * Function: 账户类 <br/>  
 * date: 2017年10月26日 下午3:50:21 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Table(name = "igrowth_account")
public class Account {
    @Id
    private Long id;

    /**
     * 用户名称
     */
    @Column(name = "account_name")
    private String accountName;
    
    /**
     * 家长角色
     */
    @Column(name = "account_role")
    private String accountRole;
    

    /**
     * 密码
     */
    @JsonIgnore
    @Column(name = "account_password")
    private String accountPassword;

    /**
     * 用户头像
     */
    @Column(name = "account_pic")
    private String accountPic;

    /**
     * 用户手机号
     */
    @Column(name = "cellphone")
    private String cellphone;

    /**
     * 当前孩子关联账户id
     */
    @Column(name = "current_cid")
    private Long currentCid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    
    
    /**
     *账户状态-1 未激活  0正常状态
     */
    @Column(name = "account_status")
    private Integer accountStatus;
    

    
    /**
     * 所有课程学习时常
     */
    @Transient
    private Integer coureSum;
    
    /**
     * studyHoursSum
     */
    @Transient
    private Double studyHoursSum;
    
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
     * @return account_name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**  
	 * accountRole.  
	 */
	public String getAccountRole() {
		return accountRole;
	}

	/**  
	 * accountRole.  
	 */
	public void setAccountRole(String accountRole) {
		this.accountRole = accountRole;
	}

	/**
     * @return account_password
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     * @param accountPassword
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    /**
     * @return account_pic
     */
    public String getAccountPic() {
        return accountPic;
    }

    /**
     * @param accountPic
     */
    public void setAccountPic(String accountPic) {
        this.accountPic = accountPic;
    }

    /**
     * @return cellphone
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * @param cellphone
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * @return current_cid
     */
    public Long getCurrentCid() {
        return currentCid;
    }

    /**
     * @param currentCid
     */
    public void setCurrentCid(Long currentCid) {
        this.currentCid = currentCid;
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
	 * coureSum.  
	 */
	public Integer getCoureSum() {
		return coureSum;
	}

	/**  
	 * coureSum.  
	 */
	public void setCoureSum(Integer coureSum) {
		this.coureSum = coureSum;
	}

	/**  
	 * studyHoursSum.  
	 */
	public Double getStudyHoursSum() {
		return studyHoursSum;
	}

	/**  
	 * studyHoursSum.  
	 */
	public void setStudyHoursSum(Double studyHoursSum) {
		this.studyHoursSum = studyHoursSum;
	}

	/**  
	 * accountStatus.  
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}

	/**  
	 * accountStatus.  
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	
}