package com.example.biogger.controller;




import com.example.biogger.entiy.Comment;
import com.example.biogger.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public Comment saveComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }
    @GetMapping("/getAll")
    public List<Comment> getAllComments() {
        return commentService.findAllComments();
    }
}
