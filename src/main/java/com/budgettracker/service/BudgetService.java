package com.budgettracker.service;

import com.budgettracker.model.Budget;
import com.budgettracker.repository.BudgetRepository;
import com.budgettracker.strategy.BudgetStrategy;
import com.budgettracker.strategy.StrictBudgetStrategy;
import com.budgettracker.observer.BudgetNotifier;
import com.budgettracker.observer.BudgetAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    private final BudgetNotifier notifier = new BudgetNotifier();
    private final BudgetStrategy strategy = new StrictBudgetStrategy();

    public BudgetService() {
        notifier.addObserver(new BudgetAlertService());
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public void addOrUpdateBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public void checkBudgetStatus(String category, double spentAmount, double limitAmount) {
        boolean withinBudget = strategy.isWithinBudget(spentAmount, limitAmount);
        if (!withinBudget) {
            notifier.notifyObservers(category, spentAmount, limitAmount);
        }
    }
}