package com.budgettracker.controller;

import com.budgettracker.service.ReportService;
import com.budgettracker.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reports")
    public String getReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "report";
    }

    @PostMapping("/generateReport")
    public String generateReport(@RequestParam String type, @RequestParam String content) {
        reportService.generateReport(type, content);
        return "redirect:/reports";
    }
}
