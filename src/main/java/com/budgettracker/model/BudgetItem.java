package com.budgettracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "budget_items")
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private BigDecimal spentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public BudgetItem() {}

    public BudgetItem(BigDecimal amount, Budget budget, Category category) {
        this.amount = amount;
        this.spentAmount = BigDecimal.ZERO;
        this.budget = budget;
        this.category = category;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getSpentAmount() { return spentAmount; }
    public void setSpentAmount(BigDecimal spentAmount) { this.spentAmount = spentAmount; }

    public Budget getBudget() { return budget; }
    public void setBudget(Budget budget) { this.budget = budget; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public BigDecimal getRemainingAmount() {
        return amount.subtract(spentAmount);
    }

    public double getPercentageSpent() {
        if (amount.compareTo(BigDecimal.ZERO) == 0) return 0.0;
        return spentAmount.divide(amount, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    public boolean isExceeded() {
        return spentAmount.compareTo(amount) > 0;
    }

    public boolean isCloseToLimit() {
        return getPercentageSpent() > 80;
    }
}
