package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ExpenseRequest;
import com.examly.springapp.model.Expense;
import com.examly.springapp.repository.ExpenseRepository;
import com.examly.springapp.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense addExpense(String ownerEmail, ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setOwnerEmail(ownerEmail);
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getMyExpenses(String ownerEmail) {
        return expenseRepository.findByOwnerEmailOrderByIdAsc(ownerEmail);
    }
}
