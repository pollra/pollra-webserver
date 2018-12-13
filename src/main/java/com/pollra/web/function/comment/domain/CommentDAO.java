package com.pollra.web.function.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 댓글 뷰에서 보여야하는 항목
 * userId
 * commentDate
 * commentContent
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class CommentDAO {
    private String userId;
    private Timestamp commentDate;
    private String commentContent;
}
