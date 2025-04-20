package com.budgettracker.controller;

import com.budgettracker.model.Budget;
import com.budgettracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @GetMapping("/budgets")
    public String getBudgets(Model model) {
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("budget", new Budget());
        return "budget";
    }

    @PostMapping("/addBudget")
    public String addBudget(@ModelAttribute Budget budget) {
        budgetService.addOrUpdateBudget(budget);
        return "redirect:/budgets";
    }

    @GetMapping("/deleteBudget/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return "redirect:/budgets";
    }
}