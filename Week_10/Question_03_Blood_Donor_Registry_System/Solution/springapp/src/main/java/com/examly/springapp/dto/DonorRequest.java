package com.examly.springapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DonorRequest {

    @NotBlank(message = "Donor name is required")
    @Size(max = 50, message = "Donor name must not exceed 50 characters")
    private String donorName;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood group must be one of A+, A-, B+, B-, AB+, AB-, O+, O-")
    private String bloodGroup;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Donor must be at least 18 years old")
    @Max(value = 65, message = "Donor must not be older than 65 years")
    private Integer age;

    @NotNull(message = "Weight is required")
    @Min(value = 50, message = "Weight must be at least 50 kg")
    @Max(value = 200, message = "Weight must not exceed 200 kg")
    private Integer weightKg;

    @NotNull(message = "Previous donations count is required")
    @Min(value = 0, message = "Previous donations cannot be negative")
    @Max(value = 100, message = "Previous donations must not exceed 100")
    private Integer previousDonations;

    @NotBlank(message = "City is required")
    private String city;

    public DonorRequest() {
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Integer weightKg) {
        this.weightKg = weightKg;
    }

    public Integer getPreviousDonations() {
        return previousDonations;
    }

    public void setPreviousDonations(Integer previousDonations) {
        this.previousDonations = previousDonations;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
