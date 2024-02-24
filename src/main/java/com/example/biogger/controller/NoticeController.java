package com.example.biogger.controller;

import com.example.biogger.entiy.Notice;
import com.example.biogger.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/getall")
    public List<Notice> getAllNotices(){
        return noticeService.findAllNotices();
    }
}
