package net.javaguides.peptides_backend.controller;

import net.javaguides.peptides_backend.dto.PeptideChartData;
import net.javaguides.peptides_backend.service.PeptideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin

@Transactional
@RestController
//Karthicksudhan

@RequestMapping(value = "/api/peptide")
public class peptideController {

    @Autowired(required=true)
    PeptideService peptideService;

    @RequestMapping(value = "/searchPeptide", method = RequestMethod.GET)
    public List<Map<String, Object>> searchPeptide(@RequestParam(value = "category", required = true) String category,
                                                   @RequestParam(value = "searchValue", required = true) String searchValue,
                                                   @RequestParam(value = "comparison", required = false) String comparison) {
        // Clean up the searchValue by trimming, removing newlines and extra spaces
        searchValue = searchValue.trim(); // Remove leading/trailing whitespace
        searchValue = searchValue.replaceAll("\\s+", " "); // Replace multiple spaces/newlines/tabs with a single space
        searchValue = searchValue.replaceAll("[\n\r]", ""); // Remove newline and carriage return characters

        // Handle the default value for comparison
        if (comparison == null || comparison.trim().isEmpty()) {
            comparison = "equal"; // Default comparison if not provided
        }

        // Call the service to get the results with the comparison operator
        return peptideService.getPeptide(category, searchValue, comparison);
    }


    @RequestMapping(value = "/blast",method = RequestMethod.GET)
    public String blast(@RequestParam (value = "blastSequence",required = true) String blastSequence){
        blastSequence = blastSequence.trim(); // Remove leading/trailing whitespace

        return peptideService.blast(blastSequence);
    }
    @RequestMapping(value = "/bieChart", method = RequestMethod.GET)
    public Map<String, List<PeptideChartData>> bieChart(@RequestParam(value = "bieChart", required = true) String bieChart) {
        // Call the service layer to fetch the chart data, which now returns a Map
        return peptideService.bieChart(bieChart);
    }

}

