package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ParticipantRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Participant;
import com.examly.springapp.repository.ParticipantRepository;
import com.examly.springapp.service.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public Participant registerParticipant(ParticipantRequest request) {
        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setEmail(request.getEmail());
        participant.setPhone(request.getPhone());
        participant.setCollegeCode(request.getCollegeCode());
        participant.setEventName(request.getEventName());
        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + id));
    }
}
