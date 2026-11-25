package com.examly.springapp.service;

import com.examly.springapp.dto.BusPassRequest;
import com.examly.springapp.model.BusPass;

public interface BusPassService {

    BusPass issuePass(BusPassRequest request);

    BusPass getPassById(Long id);

    BusPass renewPass(Long id, Integer months);
}
