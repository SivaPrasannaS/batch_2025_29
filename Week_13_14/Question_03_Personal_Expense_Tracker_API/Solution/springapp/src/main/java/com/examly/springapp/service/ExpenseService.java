package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ExpenseRequest;
import com.examly.springapp.model.Expense;

public interface ExpenseService {

    Expense addExpense(String ownerEmail, ExpenseRequest request);

    List<Expense> getMyExpenses(String ownerEmail);
}
