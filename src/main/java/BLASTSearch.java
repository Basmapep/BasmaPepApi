

import java.io.BufferedReader;
import java.io.InputStreamReader;



public class BLASTSearch {

    public static void main(String[] args) {
        // Define your query file
        String queryFile = "C:\\karthick\\personal\\SathishProjects\\datafiles\\peptides.fasta"; // Path to the peptide.fasta file

        // Paths and settings
        String databasePath = "C:\\karthick\\personal\\SathishProjects\\datafiles\\peptide_db"; // Path to the BLAST database

        // Full path to the blastp executable
        String blastExecutable = "C:\\Program Files\\NCBI\\blast-2.16.0+\\bin\\blastp.exe"; // Update with the full path to blastp.exe

        try {
            // Build the BLAST command
            String command = blastExecutable + " -query " + queryFile + " -db " + databasePath + " -outfmt 7";
            System.out.println("Running BLAST command: " + command); // Debug print

            ProcessBuilder pb = new ProcessBuilder(
                    blastExecutable,
                    "-query", queryFile,  // Use the query file
                    "-db", databasePath,  // Specify the database path
                    "-outfmt", "7"         // Tabular format
            );

            // Start the process
            Process process = pb.start();

            // Read the BLAST output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("BLAST Results:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Capture any error output
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }
            errorReader.close();

            // Close streams and wait for process to complete
            reader.close();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
