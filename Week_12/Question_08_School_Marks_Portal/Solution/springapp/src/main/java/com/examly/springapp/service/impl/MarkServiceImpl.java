package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Mark;
import com.examly.springapp.repository.MarkRepository;
import com.examly.springapp.service.MarkService;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    public MarkServiceImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    @Override
    public Mark addMark(Mark mark) {
        mark.setId(null);
        return markRepository.save(mark);
    }

    @Override
    public boolean deleteMark(Long id) {
        if (!markRepository.existsById(id)) {
            return false;
        }
        markRepository.deleteById(id);
        return true;
    }
}
