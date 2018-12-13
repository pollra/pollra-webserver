package com.pollra.persistence;

import com.pollra.web.function.comment.domain.CommentDAO;
import com.pollra.web.function.comment.domain.CommentDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository {
    @Select("SELECT pollra_userinfo.user_nik," +
            "       pollra_comment.comment_date," +
            "       pollra_comment.comment_content" +
            " FROM pollra_comment" +
            " INNER JOIN pollra_userinfo" +
            " ON pollra_comment.user_num = pollra_userinfo.user_num" +
            " WHERE pollra_comment.posts_num = #{param1};")
    public CommentDAO[] selectArrayToPostNum(int postNum);

    @Select("SELECT COUNT(*)" +
            " FROM pollra_comment" +
            " WHERE posts_num = #{param1}")
    public int selectOneToPostNum(int postNum);
}
