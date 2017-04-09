package com.touch6.business.dto.init.article;

/**
 * Created by LONG on 2017/4/7.
 */
public class ArticleCategoryDto {
    private String categoryCode;
    private String parentCategory;
    private String categoryName;

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
