package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.MethodMetric;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.utils.MethodMetricsRegistry;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MethodMetricsRegistry registry;

    public MetricsController(MethodMetricsRegistry registry) {
        this.registry = registry;
    }

    @GetMapping
    public ResponseEntity<List<MethodMetric>> getAllMetrics() {
        return ResponseEntity.ok(registry.getAll());
    }

    @GetMapping("/{methodName}")
    public ResponseEntity<MethodMetric> getMetric(@PathVariable String methodName) {
        return ResponseEntity.ok(registry.get(methodName)
                .orElseThrow(() -> new ResourceNotFoundException("No metrics recorded for method: " + methodName)));
    }
}
