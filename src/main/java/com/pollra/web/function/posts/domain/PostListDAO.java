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
public class PostListDAO {
    private String categoryName;
    private String postTitle;
    private int postNum;
    private Timestamp postDate;
}
