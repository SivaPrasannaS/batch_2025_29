package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Mark;

public interface MarkService {

    List<Mark> getAllMarks();

    Mark addMark(Mark mark);

    boolean deleteMark(Long id);
}
