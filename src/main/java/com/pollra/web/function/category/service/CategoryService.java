package com.pollra.web.function.category.service;

import com.pollra.persistence.CategoryRepository;
import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto[] selectCategoryList(){ return categoryRepository.selectCategory();}

    public CategoryDto[] selectCategoriesToLevel(int userNum, int categoryLevel){
        return categoryRepository.selectCategoriesToLevel(userNum, categoryLevel);
    }

    public CategoryDto[] selectNumToName(String categoryName){
        return categoryRepository.selectNumToName(categoryName);
    }

    public CategoryLatelyPostDAO[] selectJoinLatelyPosts(){return categoryRepository.selectJoinLatelyPosts();}

    public CategoryDto[] selectCategoryToUserNum(int userNum){return categoryRepository.selectCategoryToUserNum(userNum);}

    public CategoryLatelyPostDAO[] selectJoinLatelyPostsToUserNum(int userNum){return categoryRepository.selectJoinLatelyPostsToUserNum(userNum);}

    public int insertDefaultCategory(CategoryDto defaultCategory) {
        return categoryRepository.insertDefaultCategory(defaultCategory);
    }
}
