package com.pollra.web.function.posts.service;

import com.pollra.persistence.PostsRepository;
import com.pollra.web.function.posts.domain.PostViewDAO;
import org.springframework.stereotype.Service;

@Service
public class PostViewService {
    private PostsRepository postsRepository;

    public PostViewService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public PostViewDAO[] selectAllPostView(int userNum, int categoryNum, int postNum){
        return postsRepository.selectAllPostViewToUNumCNum(userNum, categoryNum, postNum);
    }
}
