package com.examly.springapp.dto;

public class MethodMetric {

    private String methodName;
    private long invocationCount;
    private long totalTimeMs;
    private long maxTimeMs;

    public MethodMetric() {
    }

    public MethodMetric(String methodName, long invocationCount, long totalTimeMs, long maxTimeMs) {
        this.methodName = methodName;
        this.invocationCount = invocationCount;
        this.totalTimeMs = totalTimeMs;
        this.maxTimeMs = maxTimeMs;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getInvocationCount() {
        return invocationCount;
    }

    public void setInvocationCount(long invocationCount) {
        this.invocationCount = invocationCount;
    }

    public long getTotalTimeMs() {
        return totalTimeMs;
    }

    public void setTotalTimeMs(long totalTimeMs) {
        this.totalTimeMs = totalTimeMs;
    }

    public long getMaxTimeMs() {
        return maxTimeMs;
    }

    public void setMaxTimeMs(long maxTimeMs) {
        this.maxTimeMs = maxTimeMs;
    }
}
