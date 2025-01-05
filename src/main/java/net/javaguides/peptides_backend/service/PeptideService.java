package net.javaguides.peptides_backend.service;

import net.javaguides.peptides_backend.dto.PeptideChartData;

import java.util.List;
import java.util.Map;

public interface PeptideService {
    public List<Map<String, Object>> getPeptide(String category, String param, String comparison);
    String blast(String blastSequence );
    Map<String, List<PeptideChartData>> bieChart(String bieChart);
}
