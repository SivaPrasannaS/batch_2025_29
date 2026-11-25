package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.VisitorPass;

public interface VisitorPassService {

    List<VisitorPass> getAllVisitorPasses();

    VisitorPass addVisitorPass(VisitorPass visitorPass);
}
