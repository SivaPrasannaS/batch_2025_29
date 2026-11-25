package com.examly.springapp.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.examly.springapp.dto.MethodMetric;

@Component
public class MethodMetricsRegistry {

    private final Map<String, MethodMetric> metrics = new ConcurrentHashMap<>();

    public synchronized void record(String methodName, long elapsedMs) {
        MethodMetric metric = metrics.computeIfAbsent(methodName, name -> new MethodMetric(name, 0, 0, 0));
        metric.setInvocationCount(metric.getInvocationCount() + 1);
        metric.setTotalTimeMs(metric.getTotalTimeMs() + elapsedMs);
        metric.setMaxTimeMs(Math.max(metric.getMaxTimeMs(), elapsedMs));
    }

    public synchronized List<MethodMetric> getAll() {
        List<MethodMetric> list = new ArrayList<>();
        for (MethodMetric metric : metrics.values()) {
            list.add(new MethodMetric(metric.getMethodName(), metric.getInvocationCount(), metric.getTotalTimeMs(),
                    metric.getMaxTimeMs()));
        }
        list.sort(Comparator.comparing(MethodMetric::getMethodName));
        return list;
    }

    public synchronized Optional<MethodMetric> get(String methodName) {
        MethodMetric metric = metrics.get(methodName);
        if (metric == null) {
            return Optional.empty();
        }
        return Optional.of(new MethodMetric(metric.getMethodName(), metric.getInvocationCount(),
                metric.getTotalTimeMs(), metric.getMaxTimeMs()));
    }
}
