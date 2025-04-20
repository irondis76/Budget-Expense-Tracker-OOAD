package com.budgettracker.controller;

import com.budgettracker.model.Expense;
import com.budgettracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public String getExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        model.addAttribute("expense", new Expense());
        return "expense";
    }

    @PostMapping("/addExpense")
    public String addExpense(@ModelAttribute Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        expenseService.addExpense(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
