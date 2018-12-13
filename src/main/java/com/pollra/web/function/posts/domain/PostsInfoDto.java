package com.pollra.web.function.posts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
public class PostsInfoDto {
    private int postsNum;
    private int infoNum;
    private int infoViews;
    private int infoGood;
    private Timestamp infoDate;

    public PostsInfoDto(int postsNum, Timestamp infoDate) {
        this.postsNum = postsNum;
        this.infoDate = infoDate;
    }
}
