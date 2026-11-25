package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.EquipmentRequest;
import com.examly.springapp.model.Equipment;
import com.examly.springapp.service.EquipmentService;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody EquipmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.addEquipment(request));
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @PostMapping("/{id}/lend")
    public ResponseEntity<Equipment> lendEquipment(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(equipmentService.lendEquipment(id, quantity));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Equipment> returnEquipment(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(equipmentService.returnEquipment(id, quantity));
    }
}
