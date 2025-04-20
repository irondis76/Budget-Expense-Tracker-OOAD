package com.budgettracker.util;

import com.budgettracker.model.Expense;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {
    public static void exportExpenses(List<Expense> expenses, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Date,Category,Amount,Description\n");
            for (Expense expense : expenses) {
                writer.write(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount() + "," + expense.getDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
