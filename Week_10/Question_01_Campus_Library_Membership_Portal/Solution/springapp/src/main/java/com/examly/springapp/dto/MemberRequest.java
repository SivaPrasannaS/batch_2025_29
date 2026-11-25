package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MemberRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullName;

    @NotBlank(message = "Student ID is required")
    @Size(min = 6, max = 10, message = "Student ID must be between 6 and 10 characters")
    private String studentId;

    @NotBlank(message = "Department is required")
    @Size(max = 30, message = "Department must not exceed 30 characters")
    private String department;

    @NotNull(message = "Max books allowed is required")
    private Integer maxBooksAllowed;

    public MemberRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(Integer maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }
}
