package net.javaguides.peptides_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import net.javaguides.peptides_backend.dto.PeptideChartData;
import net.javaguides.peptides_backend.entity.peptide_1121_r1;
import net.javaguides.peptides_backend.repository.PeptideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
@Transactional
public class PeptideServiceImpl implements PeptideService {

    @Autowired
    PeptideRepository peptideRep;

    @PersistenceContext
    private EntityManager entityManager;


    @Override

    public List<Map<String, Object>> getPeptide(String category, String param, String comparison) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // Map for column names based on categories
        Map<String, String> categoryColumnMap = Map.of(
                "Accession", "\"1121_r1_accession\"",
                "Score", "\"1121_r1_score\"",
                "Peptide sequence", "\"1121_r1_peptide_seq\"",
                "Peptide modification", "\"1121_r1_peptide_modification\"",
                "Peptide Length", "\"1121_r1_peptide_seq_length\"",
                "Peptide Mass", "\"1121_r1_avg_mass\""
        );

        // Check if category exists in the map
        String columnName = categoryColumnMap.get(category);
        if (columnName == null) {
            System.err.println("Invalid category: " + category);
            return resultList; // Return empty list for invalid category
        }

        // Handle null or empty param
        if (param == null || param.trim().isEmpty()) {
            System.err.println("Search parameter cannot be null or empty");
            return resultList; // Return empty list for invalid param
        }

        // Dynamically build the condition based on the comparison operator
        StringBuilder query = new StringBuilder("SELECT * FROM public.\"1121_r1\" WHERE ");

        // Ensure valid comparison operator
        if (comparison == null || comparison.trim().isEmpty()) {
            comparison = "equal"; // Default comparison if not provided
        }

        // Always wrap parameter in single quotes
        String formattedParam = "'" + param.trim() + "'"; // Always wrap the parameter in quotes

        // Build the query based on the comparison operator
        switch (comparison) {
            case "greaterthan":
                query.append(columnName).append("::Numeric > ").append(formattedParam);
                break;
            case "lesserthan":
                query.append(columnName).append(" ::Numeric < ").append(formattedParam);
                break;
            case "equal":
                query.append(columnName).append("  = ").append(formattedParam);
                break;
            case "greterthanequal":
                query.append(columnName).append(" ::Numeric >= ").append(formattedParam);
                break;
            case "lesserthanequal":
                query.append(columnName).append("::Numeric  <= ").append(formattedParam);
                break;
            default:
                System.err.println("Invalid comparison operator: " + comparison);
                return resultList; // Return empty list for invalid comparison
        }

        // Print out the SQL query for debugging purposes
        System.out.println("Generated SQL Query: " + query.toString());

        // Execute the query and fetch the results
        try {
            List<Object[]> dbResults = entityManager.createNativeQuery(query.toString()).getResultList();

            // Loop through the result set and map each row to the desired structure
            for (Object[] row : dbResults) {
                Map<String, Object> resultMap = new HashMap<>();

                resultMap.put("entry", row[0]); // Assuming entry is at index 0
                resultMap.put("accession", row[1]); // Assuming accession is at index 1
                resultMap.put("description", row[2]); // Assuming description is at index 2
                resultMap.put("score", row[3]); // Assuming score is at index 3
                resultMap.put("falsePositiveRate", row[4]); // Assuming falsePositiveRate is at index 4
                resultMap.put("avgMass", row[5]); // Assuming avgMass is at index 5
                resultMap.put("peptideModification", row[6]); // Assuming peptideModification is at index 6
                resultMap.put("peptideMhp", row[7]); // Assuming peptideMhp is at index 7
                resultMap.put("peptideSeq", row[8]); // Assuming peptideSeq is at index 8
                resultMap.put("peptideSeqStart", row[9]); // Assuming peptideSeqStart is at index 9
                resultMap.put("peptideSeqLength", row[10]); // Assuming peptideSeqLength is at index 10
                resultMap.put("peptideMatchedProducts", row[11]); // Assuming peptideMatchedProducts is at index 11
                resultMap.put("precursorRetentionTime", row[12]); // Assuming precursorRetentionTime is at index 12
                resultMap.put("precursorIntensity", row[13]); // Assuming precursorIntensity is at index 13
                resultMap.put("precursorCharge", row[14]); // Assuming precursorCharge is at index 14
                resultMap.put("precursorMz", row[15]); // Assuming precursorMz is at index 15
                resultMap.put("variety", row[16]); // Assuming variety is at index 16

                // Add the mapped result to the result list
                resultList.add(resultMap);
            }

        } catch (Exception e) {
            System.err.println("Error executing query: " + e.getMessage());
        }

        return resultList;
    }





    // Method to process peptides and run BLAST
    public String blast(String blastSequence) {
        // Step 1: Get the list of peptides based on the blastSequence
        List<peptide_1121_r1> list = peptideRep.blast(blastSequence);  // Assuming peptideRep is your repository instance

        // Step 2: Write peptides to a .fasta file
        String fastaFilePath = "C:\\karthick\\personal\\SathishProjects\\datafiles\\peptides.fasta";  // Path to save FASTA file
        writePeptidesToFasta(list, fastaFilePath);

        // Step 3: Perform BLAST with the generated .fasta file
        String queryFile = "C:\\karthick\\personal\\SathishProjects\\datafiles\\peptides.fasta"; // Path to the peptide.fasta file

        // Paths and settings
        String databasePath = "C:\\karthick\\personal\\SathishProjects\\datafiles\\peptide_db"; // Path to the BLAST database

        // Full path to the blastp executable
        String blastExecutable = "C:\\Program Files\\NCBI\\blast-2.16.0+\\bin\\blastp.exe"; // Update with the full path to blastp.exe

        // Process the BLAST command and capture the results
        StringBuilder output = new StringBuilder();
        try {
            // Build the BLAST command
            String command = blastExecutable + " -query " + queryFile + " -db " + databasePath + " -outfmt 7";
            System.out.println("Running BLAST command: " + command); // Debug print

            // Use ProcessBuilder to run the BLAST command
            ProcessBuilder pb = new ProcessBuilder(
                    blastExecutable,
                    "-query", queryFile,  // Use the query file
                    "-db", databasePath,  // Specify the database path
                    "-outfmt", "7"         // Tabular format (output as tabular)
            );

            // Start the process
            Process process = pb.start();

            // Capture standard output from BLAST
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");  // Append each line of BLAST result
                }
            }

            // Capture any error output
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    output.append("Error: ").append(errorLine).append("\n");  // Append error output if exists
                }
            }

            // Wait for the process to complete
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            // Catch IO or InterruptedException and print stack trace
            e.printStackTrace();
            output.append("Error: ").append(e.getMessage()).append("\n");
        }

        // Return the captured BLAST output

        return output.toString();

    }



    public Map<String, List<PeptideChartData>> bieChart(String bieChart) {
        // SQL query string to fetch data for the chart
        String queryString = "SELECT 'Score_Range' AS category, " +
                "CASE WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 0 AND 1000 THEN '0-1000' " +
                "WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 1001 AND 2000 THEN '1001-2000' " +
                "WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 2001 AND 3000 THEN '2001-3000' " +
                "WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 3001 AND 4000 THEN '3001-4000' " +
                "WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 4001 AND 5000 THEN '4001-5000' " +
                "ELSE 'Above 5000' END AS range, " +
                "SUM(CAST(\"1121_r1_score\" AS NUMERIC)) AS total_sum " +
                "FROM (SELECT DISTINCT CAST(\"1121_r1_score\" AS NUMERIC) " +
                "FROM \"1121_r1\" WHERE CAST(\"1121_r1_score\" AS NUMERIC) <= 5000) t " +
                "GROUP BY range " +
                "UNION ALL " +
                "SELECT 'Peptide_Length_Range' AS category, " +
                "CASE WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 0 AND 20 THEN '0-20' " +
                "WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 21 AND 40 THEN '21-40' " +
                "WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 41 AND 60 THEN '41-60' " +
                "WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 61 AND 80 THEN '61-80' " +
                "WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 81 AND 100 THEN '81-100' " +
                "ELSE 'Above 100' END AS range, " +
                "SUM(CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC)) AS total_sum " +
                "FROM (SELECT DISTINCT CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) " +
                "FROM \"1121_r1\" WHERE CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) <= 100) t " +
                "GROUP BY range " +
                "ORDER BY category, range";

        // Create the query using the EntityManager
        Query query = entityManager.createNativeQuery(queryString);

        // Execute the query and get the result list
        List<Object[]> resultList = query.getResultList();

        // Lists to store mapped peptideDtos for each category
        List<PeptideChartData> peptideLengthRangeData = new ArrayList<>();
        List<PeptideChartData> scoreRangeData = new ArrayList<>();

        // Iterate over the result list and map each record to peptideDto
        for (Object[] result : resultList) {
            String category = (String) result[0];  // category (either Score Range or Peptide Length Range)
            String range = (String) result[1];     // range (e.g., '0-1000', '21-40', etc.)
            String totalSum = result[2].toString(); // total sum (as String)

            // Construct peptideDto using the constructor that accepts the fields
            PeptideChartData dto = new PeptideChartData(
                    category,  // entry (Category: Score Range or Peptide Length Range)
                    range,     // peptideSeqLength (We map "range" directly here for visualization)
                    totalSum   // variety (mapped to totalSum)
            );

            // Add the constructed dto to the appropriate list based on the category
            if ("Score_Range".equals(category)) {
                scoreRangeData.add(dto);
            } else if ("Peptide_Length_Range".equals(category)) {
                peptideLengthRangeData.add(dto);
            }
        }

        // Create a map to hold both lists
        Map<String, List<PeptideChartData>> resultMap = new HashMap<>();
        resultMap.put("Peptide_Length_Range", peptideLengthRangeData);
        resultMap.put("Score_Range", scoreRangeData);

        // Return the map containing both lists
        return resultMap;
    }




    // Method to write peptides to a FASTA file
    private void writePeptidesToFasta(List<peptide_1121_r1> peptides, String fastaFilePath) {
        // Use a set to keep track of already seen accession numbers
        Set<String> seenAccessions = new HashSet<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fastaFilePath))) {
            for (peptide_1121_r1 peptide : peptides) {
                String accession = peptide.getAccession();  // Access the peptide accession
                String peptideSeq = peptide.getPeptideSeq();  // Access the peptide sequence

                // Only write if the accession has not been written before
                if (peptideSeq != null && !peptideSeq.isEmpty() && !seenAccessions.contains(accession)) {
                    // Add the accession to the set to prevent duplicates
                    seenAccessions.add(accession);

                    // Write the accession and sequence to the FASTA file
                    writer.write(">" + accession + "\n");
                    writer.write(peptideSeq + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}