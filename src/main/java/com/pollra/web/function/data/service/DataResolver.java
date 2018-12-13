package com.pollra.web.function.data.service;

import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import com.pollra.web.function.posts.domain.PostListDAO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataResolver {
    // 페이지 이름별로 데이터이름 바꿔서 서브카테고리에 전송
    public CategoryDto[] goPageCategoryList(CategoryLatelyPostDAO[] latelyPosts,
                                            CategoryDto[] categoryDto){

        CategoryDto[] result = categoryDto;
        for(CategoryDto category:categoryDto){
            for(CategoryLatelyPostDAO lately : latelyPosts){
                if(category.getCategoryName().equals(lately.getCategoryName()))
                    category.setCategoryNum(lately.getPostsNum());
            }
        }
        return result;
    }
    public PostListDAO[] goPagePostList(CategoryLatelyPostDAO[] latelyPosts,
                                            PostListDAO[] postListDAOS){

        PostListDAO[] result = postListDAOS;
        for(PostListDAO category:postListDAOS){
            for(CategoryLatelyPostDAO lately : latelyPosts){
                if(category.getCategoryName().equals(lately.getCategoryName()))
                    category.setPostNum(lately.getPostsNum());
            }
        }
        return result;
    }
}
