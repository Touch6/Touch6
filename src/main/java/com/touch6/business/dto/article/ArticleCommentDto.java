package com.touch6.business.dto.article;

import java.util.Date;

/**
 * Created by xuan.touch6@qq.com on 2017/4/11.
 */
public class ArticleCommentDto {
    private String id;
    private String articleId;
    private String sponsorId;
    private Integer floor;
    private Integer follows;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getFollows() {
        return follows;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
