package com.touch6.business.dto.init.article;

/**
 * Created by LONG on 2017/4/7.
 */
public class ArticleTypeDto {
    private String type;//original：原创  reprint：转载  translation：翻译
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
