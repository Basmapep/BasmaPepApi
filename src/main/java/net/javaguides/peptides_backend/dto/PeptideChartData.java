package net.javaguides.peptides_backend.dto;

import lombok.Data;

@Data
public class PeptideChartData {
    private String category;
    private String range;
    private Double totalSum;

    // Constructor, getters, setters
    public PeptideChartData(String category, String range, String totalSum) {
        this.category = category;
        this.range = range;
        this.totalSum = Double.valueOf(totalSum);
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }
}