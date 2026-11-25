package com.examly.springapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.BusPassRequest;
import com.examly.springapp.exception.InvalidRenewalException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.BusPass;
import com.examly.springapp.repository.BusPassRepository;
import com.examly.springapp.service.BusPassService;

@Service
public class BusPassServiceImpl implements BusPassService {

    private static final Logger logger = LoggerFactory.getLogger(BusPassServiceImpl.class);

    private final BusPassRepository busPassRepository;

    public BusPassServiceImpl(BusPassRepository busPassRepository) {
        this.busPassRepository = busPassRepository;
    }

    @Override
    public BusPass issuePass(BusPassRequest request) {
        BusPass pass = new BusPass();
        pass.setStudentName(request.getStudentName());
        pass.setRouteNumber(request.getRouteNumber());
        pass.setMonthsRemaining(request.getMonths());
        BusPass saved = busPassRepository.save(pass);
        logger.info("Bus pass issued to {} on route {}", saved.getStudentName(), saved.getRouteNumber());
        return saved;
    }

    @Override
    public BusPass getPassById(Long id) {
        return busPassRepository.findById(id).orElseThrow(() -> {
            logger.warn("Bus pass not found with id {}", id);
            return new ResourceNotFoundException("Bus pass not found with id: " + id);
        });
    }

    @Override
    public BusPass renewPass(Long id, Integer months) {
        logger.debug("Renewal request received for pass {} with {} months", id, months);
        if (months == null || months < 1 || months > 12) {
            logger.error("Invalid renewal period {} for pass {}", months, id);
            throw new InvalidRenewalException("Renewal period must be between 1 and 12 months");
        }
        BusPass pass = getPassById(id);
        pass.setMonthsRemaining(pass.getMonthsRemaining() + months);
        BusPass saved = busPassRepository.save(pass);
        logger.info("Bus pass {} renewed. Months remaining: {}", saved.getId(), saved.getMonthsRemaining());
        return saved;
    }
}
