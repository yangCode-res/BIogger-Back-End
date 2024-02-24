package com.example.biogger.service;

import com.example.biogger.dao.NoticeRepository;
import com.example.biogger.entiy.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    public List<Notice> findAllNotices()
    {
        return noticeRepository.findAll();
    }
}
