package com.pollra.web.function.category.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CategoryLatelyPostDAO {
    private String categoryName;
    private int postsNum;
    private Timestamp postsDate;

    public CategoryLatelyPostDAO(String categoryName, int postsNum) {
        this.categoryName = categoryName;
        this.postsNum = postsNum;
    }

    @Override
    public String toString() {
        return "CategoryLatelyPostDAO{" +
                "categoryName='" + categoryName + '\'' +
                ", postsNum=" + postsNum +
                '}';
    }
}
