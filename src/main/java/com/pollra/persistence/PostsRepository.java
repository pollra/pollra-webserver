package com.pollra.persistence;

import com.pollra.web.function.posts.domain.PostListDAO;
import com.pollra.web.function.posts.domain.PostViewDAO;
import com.pollra.web.function.posts.domain.PostsDto;
import com.pollra.web.function.posts.domain.PostsInfoDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostsRepository {
    @Select("SELECT pollra_category.category_name," +
            " pollra_posts.posts_title," +
            " pollra_posts.posts_num," +
            " pollra_posts.posts_date" +
            " FROM pollra_posts" +
            " INNER JOIN pollra_category" +
            " ON pollra_category.category_num=pollra_posts.category_num" +
            " WHERE pollra_category.category_name = #{param1}")
    public PostListDAO[] selectPostListToCategoryName(String categoryName);

    @Select("SELECT * FROM pollra_posts" +
            " WHERE pollra_posts.user_num=#{param1}" +
            " AND pollra_posts.category_num=#{param2}" +
            " ORDER BY pollra_posts.posts_num")
    public PostsDto selectPostsToUsernumCategorynum(int userNum, int categoryNum);

    @Select("SELECT pollra_posts.posts_num," +
            " pollra_posts.user_num," +
            " pollra_posts.posts_date," +
            " pollra_postsinfo.info_views," +
            " pollra_postsinfo.info_good," +
            " pollra_posts.posts_title," +
            " pollra_posts.posts_content" +
            " FROM pollra_postsinfo" +
            " INNER JOIN pollra_posts ON pollra_posts.posts_num=pollra_postsinfo.posts_num" +
            " WHERE pollra_posts.user_num=#{param1} " +
            " AND pollra_posts.category_num=#{param2} " +
            " AND pollra_posts.posts_num=#{param3}")
    public PostViewDAO[] selectAllPostViewToUNumCNum(int userNum, int categoryNum, int postNum);

    @Select("SELECT pollra_posts.posts_num" +
            " FROM pollra_posts" +
            " WHERE posts_num=#{param1}")
    public int selectOnePostCheck(int postNum);

    @Select("SELECT pollra_category.category_name," +
            " pollra_posts.posts_title," +
            " pollra_posts.posts_num," +
            " pollra_posts.posts_date" +
            " FROM pollra_posts" +
            " INNER JOIN pollra_category" +
            " ON pollra_category.category_num=pollra_posts.category_num" +
            " WHERE pollra_category.user_num = #{param1}")
    public PostListDAO[] selectArrayPostListDAOToUserNum(int userNum);

    @Transactional
    @Insert("INSERT INTO pollra_posts (user_num, category_num, posts_title, posts_content, posts_date)" +
            " VALUES (#{userNum}, #{categoryNum}, #{postsTitle}, #{postsContent}, #{postsDate})")
    public int insertDefaultPosting(PostsDto defaultPosting);

    @Transactional
    @Insert("INSERT INTO pollra_postsinfo (posts_num, info_date)" +
            " VALUES (#{postsNum}, #{infoDate})")
    public int insertDefaultPostsInfo(PostsInfoDto defaultPostInfo);
}
