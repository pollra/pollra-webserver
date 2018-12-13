package com.pollra.web.function.comment.domain;

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
public class CommentDto {
    private int postsNum;
    private int userNum;
    private int commentNum;
    private int commentParent;
    private int commentLevel;
    private Timestamp commentDate;
    private String commentContent;
    private int commentOrder;
}
