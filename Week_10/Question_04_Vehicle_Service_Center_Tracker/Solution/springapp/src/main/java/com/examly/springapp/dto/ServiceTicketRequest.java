package com.examly.springapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ServiceTicketRequest {

    @NotBlank(message = "Vehicle number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$", message = "Vehicle number must follow the format TN01AB1234")
    private String vehicleNumber;

    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 50, message = "Owner name must be between 3 and 50 characters")
    private String ownerName;

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @NotNull(message = "Estimated cost is required")
    @Min(value = 500, message = "Estimated cost must be at least 500")
    private Double estimatedCost;

    public ServiceTicketRequest() {
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
