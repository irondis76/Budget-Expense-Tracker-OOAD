package com.budgettracker.controller;

import com.budgettracker.model.BudgetItem;
import com.budgettracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class BudgetItemController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/addBudgetItem")
    public String addBudgetItem(@RequestParam Long budgetId,
                                @RequestParam Long categoryId,
                                @RequestParam BigDecimal amount) {
        budgetService.addOrUpdateBudgetItem(budgetId, categoryId, amount);
        return "redirect:/budgets";
    }

    @GetMapping("/deleteBudgetItem/{id}")
    public String deleteBudgetItem(@PathVariable Long id) {
        budgetService.deleteBudgetItem(id);
        return "redirect:/budgets";
    }
}