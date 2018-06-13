package com.github.wxiaoqi.security.api.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "igrowth_message")
public class Message {
    @Id
    private Integer id;

    /**
     * 消息内容
     */
    @Column(name = "message_content")
    private String messageContent;

    /**
     * 消息标题
     */
    @Column(name = "message_title")
    private String messageTitle;

    /**
     * 消息发送时间
     */
    @Column(name = "message_time")
    private Date messageTime;

    /**
     * 消息类型 0系统通知  1评论,回复通知 2外链
     */
    @Column(name = "message_type")
    private Integer messageType;

    /**
     * 消息url
     */
    @Column(name = "message_url")
    private String messageUrl;
    
    /**
     * 消息状态 0未读 1已读 2删除
     */
    @Column(name = "message_status")
    private Integer messageStatus;

    /**
     * 用户id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return message_content
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * @param messageContent
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * @return message_title
     */
    public String getMessageTitle() {
        return messageTitle;
    }

    /**
     * @param messageTitle
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    /**
     * @return message_time
     */
    public Date getMessageTime() {
        return messageTime;
    }

    /**
     * @param messageTime
     */
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * @return message_type
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * @param messageType
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * @return message_url
     */
    public String getMessageUrl() {
        return messageUrl;
    }

    /**
     * @param messageUrl
     */
    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
    
    public Integer getMessageStatus() {
		return messageStatus;
	}

    /**
     * @param messageUrl
     */
	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
     * @return account_id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}