package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ParticipantRequest;
import com.examly.springapp.model.Participant;

public interface ParticipantService {

    Participant registerParticipant(ParticipantRequest request);

    List<Participant> getAllParticipants();

    Participant getParticipantById(Long id);
}
