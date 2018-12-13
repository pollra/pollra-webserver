package com.pollra.persistence;

import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository {
    @Select("SELECT *" +
            " FROM pollra_category" +
            " WHERE user_num=#{param1}" +
            " AND category_level=#{param2}")
    public CategoryDto[] selectCategoriesToLevel(int userNum, int categoryLevel);

    @Select("SELECT *" +
            " FROM pollra_category" +
            " WHERE category_name = #{param1}")
    public CategoryDto[] selectNumToName(String categoryName);

    @Select("SELECT category_name, posts_num,posts_date" +
            " FROM pollra_category" +
            " INNER JOIN pollra_posts" +
            " ON pollra_category.category_num=pollra_posts.category_num" +
            " ORDER BY posts_date desc")
    public CategoryLatelyPostDAO[] selectJoinLatelyPosts();

    @Select("SELECT pollra_category.category_name," +
            " pollra_posts.posts_num," +
            " pollra_posts.posts_date" +
            " FROM pollra_category" +
            " INNER JOIN pollra_posts" +
            " ON pollra_category.category_num=pollra_posts.category_num" +
            " WHERE pollra_category.user_num = #{param1}" +
            " ORDER BY posts_date desc")
    public CategoryLatelyPostDAO[] selectJoinLatelyPostsToUserNum(int userNum);

    @Select("SELECT *" +
            " FROM pollra_category")
    public CategoryDto[] selectCategory();

    @Select("SELECT *" +
            " FROM pollra_category" +
            " WHERE user_num =#{param1}" +
            " ORDER BY category_num DESC")
    public CategoryDto[] selectCategoryToUserNum(int userId);

    @Transactional
    @Insert("INSERT INTO pollra_category(user_num, category_name, category_level)" +
            " VALUES (#{userNum}, #{categoryName}, #{categoryLevel})")
    public int insertDefaultCategory(CategoryDto defaultCategory);
}
