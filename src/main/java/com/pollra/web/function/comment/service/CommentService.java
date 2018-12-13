package com.pollra.web.function.comment.service;

import com.pollra.persistence.CommentRepository;
import com.pollra.web.function.comment.domain.CommentDAO;
import com.pollra.web.function.comment.domain.CommentDto;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDAO[] selectArrayToPostNum(int postNum){
        return commentRepository.selectArrayToPostNum(postNum);
    }
    public int selectOneToPostNum(int postNum) { return commentRepository.selectOneToPostNum(postNum); }
}
