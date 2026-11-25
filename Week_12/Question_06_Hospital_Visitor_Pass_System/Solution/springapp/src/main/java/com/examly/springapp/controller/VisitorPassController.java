package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.VisitorPass;
import com.examly.springapp.service.VisitorPassService;

@RestController
@RequestMapping("/api/visitor-passes")
public class VisitorPassController {

    private final VisitorPassService visitorPassService;

    public VisitorPassController(VisitorPassService visitorPassService) {
        this.visitorPassService = visitorPassService;
    }

    @GetMapping
    public ResponseEntity<List<VisitorPass>> getAllVisitorPasses() {
        return ResponseEntity.ok(visitorPassService.getAllVisitorPasses());
    }

    @PostMapping
    public ResponseEntity<VisitorPass> addVisitorPass(@RequestBody VisitorPass visitorPass) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitorPassService.addVisitorPass(visitorPass));
    }
}
