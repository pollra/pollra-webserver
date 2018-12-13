package com.pollra.web.function.posts.service;

import com.pollra.persistence.PostsRepository;
import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.page.PageVO;
import com.pollra.web.function.posts.domain.PostListDAO;
import org.springframework.stereotype.Service;

@Service
public class PostListService {
    private PostsRepository postsRepository;

    public PostListService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public PostListDAO[] getPostListToCategoryName(String categoryName){
        return postsRepository.selectPostListToCategoryName(categoryName);
    }

    public PostListDAO[] selectArrayPostListDAOToUserNum(int userNum){return postsRepository.selectArrayPostListDAOToUserNum(userNum);}

    // user의 정보를 받아서 소유한 카테고리 중 가장 최근에 등록된 PageVO를 리턴함
    public PageVO getPageVOToUserInfoDto(UserInfoDto infoDto) throws Exception{
        PostListDAO[] listDAO = postsRepository.selectArrayPostListDAOToUserNum(infoDto.getUserNum());
        return new PageVO(infoDto.getUserNik(),listDAO[0].getCategoryName(),listDAO[0].getPostNum()+"");
    }
}
