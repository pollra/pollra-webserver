package com.pollra.web.function.category.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
    private int userNum;
    private int categoryNum;
    private int categoryParent;
    private int categoryOrder;
    private String categoryName;
    private int categoryLevel;

    public CategoryDto(int userNum, String categoryName, int categoryLevel) {
        this.userNum = userNum;
        this.categoryName = categoryName;
        this.categoryLevel = categoryLevel;
    }
}
