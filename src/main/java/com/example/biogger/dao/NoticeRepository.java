package com.example.biogger.dao;

import com.example.biogger.entiy.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository  extends JpaRepository<Notice, Integer> {

}
