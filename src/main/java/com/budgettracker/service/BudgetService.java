package com.budgettracker.service;

import com.budgettracker.model.Budget;
import com.budgettracker.model.BudgetItem;
import com.budgettracker.model.Category;
import com.budgettracker.model.Expense;
import com.budgettracker.repository.BudgetItemRepository;
import com.budgettracker.repository.BudgetRepository;
import com.budgettracker.repository.CategoryRepository;
import com.budgettracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Budget saveBudget(Budget budget) {
        if (budget.getBudgetItems() == null) {
            budget.setBudgetItems(new ArrayList<>());
        }
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        for (Budget b : budgets) {
            if (b.getBudgetItems() != null) {
                for (BudgetItem item : b.getBudgetItems()) {
                    BigDecimal spent = calculateSpentForItem(b, item.getCategory());
                    item.setSpentAmount(spent);
                }
            }
        }
        return budgets;
    }

    public void addOrUpdateBudgetItem(Long budgetId, Long categoryId, BigDecimal amount) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        BudgetItem item = budgetItemRepository.findByBudgetAndCategory(budget, category)
                .orElse(new BudgetItem());

        item.setBudget(budget);
        item.setCategory(category);
        item.setAmount(amount);
        item.setSpentAmount(calculateSpentForItem(budget, category));

        budgetItemRepository.save(item);
    }

    private BigDecimal calculateSpentForItem(Budget budget, Category category) {
        LocalDate start = LocalDate.of(budget.getYear(), budget.getMonth(), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Expense> expenses = expenseRepository.findByCategoryAndDateBetween(category, start, end);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deleteBudgetItem(Long id) {
        budgetItemRepository.deleteById(id);
    }
}