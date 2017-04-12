package com.touch6.business.dto.article;

import java.util.Date;

/**
 * Created by PAX on 2017/4/11.
 */
public class ArticleCommentReplyDto {
    private String id;
    private String commentId;
    private String sponsorId;
    private String replierId;
    private String content;
    private Integer approvalAmount = 0;
    private Integer opposeAmount = 0;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReplierId() {
        return replierId;
    }

    public void setReplierId(String replierId) {
        this.replierId = replierId;
    }

    public Integer getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(Integer approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public Integer getOpposeAmount() {
        return opposeAmount;
    }

    public void setOpposeAmount(Integer opposeAmount) {
        this.opposeAmount = opposeAmount;
    }
}
