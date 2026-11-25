package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.VisitorPass;
import com.examly.springapp.repository.VisitorPassRepository;
import com.examly.springapp.service.VisitorPassService;

@Service
public class VisitorPassServiceImpl implements VisitorPassService {

    private final VisitorPassRepository visitorPassRepository;

    public VisitorPassServiceImpl(VisitorPassRepository visitorPassRepository) {
        this.visitorPassRepository = visitorPassRepository;
    }

    @Override
    public List<VisitorPass> getAllVisitorPasses() {
        return visitorPassRepository.findAll();
    }

    @Override
    public VisitorPass addVisitorPass(VisitorPass visitorPass) {
        visitorPass.setId(null);
        return visitorPassRepository.save(visitorPass);
    }
}
