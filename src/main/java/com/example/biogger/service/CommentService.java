package com.example.biogger.service;




import com.example.biogger.dao.CommentRepository;
import com.example.biogger.entiy.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        comment.setCommentTime(new Date()); // Set the current time as comment time
        return commentRepository.save(comment);
    }
    //查询全部评论
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}
