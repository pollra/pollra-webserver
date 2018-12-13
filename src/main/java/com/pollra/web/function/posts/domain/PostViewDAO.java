package com.pollra.web.function.posts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 포스팅 뷰에 담겨야 하는 정보
 * userId
 * postDate (포스팅 작성일)
 * postTitle
 * postViews
 * postGood
 * postComment
 * postContent
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class PostViewDAO {
    private int postNum;
    private int userNum;
    private Timestamp postDate;
    private int postViews;
    private int postGood;
    private String postTitle;
    private String postContent;
}
