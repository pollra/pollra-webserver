package com.pollra.web.function.posts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
public class PostsDto {
    private int userNum;
    private int categoryNum;
    private int postsNum;
    private String postsTitle;
    private String postsContent;
    private Timestamp postsDate;
    private int subPostNum;

    public PostsDto(int userNum, int categoryNum, String postsTitle, String postsContent, Timestamp postsDate) {
        this.userNum = userNum;
        this.categoryNum = categoryNum;
        this.postsTitle = postsTitle;
        this.postsContent = postsContent;
        this.postsDate = postsDate;
    }
}
