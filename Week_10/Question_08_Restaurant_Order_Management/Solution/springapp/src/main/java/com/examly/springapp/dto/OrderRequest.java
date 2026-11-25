package com.examly.springapp.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 40, message = "Customer name must not exceed 40 characters")
    private String customerName;

    @NotNull(message = "Table number is required")
    @Min(value = 1, message = "Table number must be between 1 and 30")
    @Max(value = 30, message = "Table number must be between 1 and 30")
    private Integer tableNumber;

    @NotNull(message = "Order must contain at least one item")
    @Size(min = 1, max = 10, message = "Order must contain between 1 and 10 items")
    @Valid
    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
