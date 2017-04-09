package com.touch6.business.dto.init.article;

/**
 * Created by LONG on 2017/4/7.
 */
public class ArticleCategoryDto {
    private String category;
    private String parentCategory;
    private String name;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }
}
