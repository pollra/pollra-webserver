package com.pollra.web.function.posts.service;

import com.pollra.persistence.PostsRepository;
import com.pollra.web.function.posts.domain.PostsDto;
import com.pollra.web.function.posts.domain.PostsInfoDto;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

    private PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public PostsDto selectOnePostToUsernumCategorynum(int userNum, int categoryNum){
        return postsRepository.selectPostsToUsernumCategorynum(userNum, categoryNum);
    }

    public int insertDefaultPosting(PostsDto defaultPosting) {
        return postsRepository.insertDefaultPosting(defaultPosting);
    }

    public int insertDefaultPostsInfo(PostsInfoDto defaultPostInfo) {
        return postsRepository.insertDefaultPostsInfo(defaultPostInfo);
    }
}
