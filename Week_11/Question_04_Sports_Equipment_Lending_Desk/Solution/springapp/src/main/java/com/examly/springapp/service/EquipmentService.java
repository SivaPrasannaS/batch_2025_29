package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.EquipmentRequest;
import com.examly.springapp.model.Equipment;

public interface EquipmentService {

    Equipment addEquipment(EquipmentRequest request);

    List<Equipment> getAllEquipment();

    Equipment getEquipmentById(Long id);

    Equipment lendEquipment(Long id, Integer quantity);

    Equipment returnEquipment(Long id, Integer quantity);
}
