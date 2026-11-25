package com.examly.springapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.BusPassRequest;
import com.examly.springapp.model.BusPass;
import com.examly.springapp.service.BusPassService;

@RestController
@RequestMapping("/api/bus-passes")
public class BusPassController {

    private final BusPassService busPassService;

    public BusPassController(BusPassService busPassService) {
        this.busPassService = busPassService;
    }

    @PostMapping
    public ResponseEntity<BusPass> issuePass(@RequestBody BusPassRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busPassService.issuePass(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusPass> getPassById(@PathVariable Long id) {
        return ResponseEntity.ok(busPassService.getPassById(id));
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<BusPass> renewPass(@PathVariable Long id, @RequestParam Integer months) {
        return ResponseEntity.ok(busPassService.renewPass(id, months));
    }
}
