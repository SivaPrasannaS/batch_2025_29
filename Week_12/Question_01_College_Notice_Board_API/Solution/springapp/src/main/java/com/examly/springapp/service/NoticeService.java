package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Notice;

public interface NoticeService {

    List<Notice> getAllNotices();

    Notice getNoticeById(Long id);
}
