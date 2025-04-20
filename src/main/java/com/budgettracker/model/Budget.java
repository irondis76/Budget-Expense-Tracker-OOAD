package com.budgettracker.model;

import jakarta.persistence.*;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private double limitAmount;
    private double actualSpent;

    public Budget() {}

    public Budget(String category, double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.actualSpent = 0.0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getLimitAmount() { return limitAmount; }
    public void setLimitAmount(double limitAmount) { this.limitAmount = limitAmount; }
    public double getActualSpent() { return actualSpent; }
    public void setActualSpent(double actualSpent) { this.actualSpent = actualSpent; }
}
