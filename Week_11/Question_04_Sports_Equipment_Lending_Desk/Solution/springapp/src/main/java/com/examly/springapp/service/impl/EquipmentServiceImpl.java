package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.EquipmentRequest;
import com.examly.springapp.exception.LendingException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Equipment;
import com.examly.springapp.repository.EquipmentRepository;
import com.examly.springapp.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment addEquipment(EquipmentRequest request) {
        Equipment equipment = new Equipment();
        equipment.setItemName(request.getItemName());
        equipment.setCategory(request.getCategory());
        equipment.setTotalQuantity(request.getTotalQuantity());
        equipment.setAvailableQuantity(request.getTotalQuantity());
        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));
    }

    @Override
    public Equipment lendEquipment(Long id, Integer quantity) {
        Equipment equipment = getEquipmentById(id);
        if (quantity > equipment.getAvailableQuantity()) {
            throw new LendingException("Only " + equipment.getAvailableQuantity() + " units of "
                    + equipment.getItemName() + " available");
        }
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() - quantity);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment returnEquipment(Long id, Integer quantity) {
        Equipment equipment = getEquipmentById(id);
        int lent = equipment.getTotalQuantity() - equipment.getAvailableQuantity();
        if (quantity > lent) {
            throw new LendingException("Cannot return more than " + lent + " units of " + equipment.getItemName());
        }
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() + quantity);
        return equipmentRepository.save(equipment);
    }
}
