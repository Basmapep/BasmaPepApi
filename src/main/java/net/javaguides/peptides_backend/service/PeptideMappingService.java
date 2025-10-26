package net.javaguides.peptides_backend.service;

import net.javaguides.peptides_backend.dto.MappingResponseDto;
import net.javaguides.peptides_backend.dto.MappingResultDto;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import com.opencsv.*;

@Service
public class PeptideMappingService {

    private final String basePath = "D:\\projects\\pasma\\"; // can be made configurable
    private final String proteomeFile = basePath + "IRGSP-1.0_protein_2025-03-19.fasta";
    private final String gffFile = basePath + "transcripts.gff";

    public MappingResponseDto processSequence(String sequence) throws Exception {
        List<String> peptides = List.of(sequence.toUpperCase());
        return runMapping(peptides);
    }

    public MappingResponseDto processBase64Input(String base64) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(base64);
        String decodedStr = new String(decoded);
        // if CSV data is provided in base64
        List<String> peptides = Arrays.stream(decodedStr.split("[,\\n\\r]+"))
                .filter(s -> !s.trim().isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
        return runMapping(peptides);
    }

    private MappingResponseDto runMapping(List<String> peptides) throws Exception {
        // Step 1: Load proteome
        LinkedHashMap<String, ProteinSequence> proteome =
                FastaReaderHelper.readFastaProteinSequence(new File(proteomeFile));

        // Step 2: Parse headers
        Map<String, ProteinInfo> proteinInfoMap = new LinkedHashMap<>();
        Pattern idPattern = Pattern.compile("(Os\\d{2}t\\d{7}-\\d{2})");
        for (Map.Entry<String, ProteinSequence> entry : proteome.entrySet()) {
            String header = entry.getKey();
            Matcher m = idPattern.matcher(header);
            if (m.find()) {
                String transcriptId = m.group(1);
                String geneId = transcriptId.replace("t", "g");
                String chrom = transcriptId.substring(0, 4);

                // ✅ Extract description text after transcript ID
                String description = header.replaceFirst(".*" + transcriptId + "\\s*", "").trim();

                proteinInfoMap.put(transcriptId,
                        new ProteinInfo(transcriptId, geneId, chrom, entry.getValue().getSequenceAsString(), description));
            }
        }

        // Step 3: Load GFF
        Map<String, GFFRecord> gffMap = loadGFF(gffFile);

        // Step 4: Mapping
        List<MappingResultDto> results = new ArrayList<>();
        for (String pep : peptides) {
            boolean found = false;
            for (ProteinInfo info : proteinInfoMap.values()) {

                int idx = info.sequence.indexOf(pep);
                if (idx != -1) {
                    found = true;
                    int startAA = idx + 1;
                    int endAA = startAA + pep.length() - 1;
                    GFFRecord g = gffMap.get(info.transcriptId);
                    MappingResultDto dto = createMappingDto(pep, info, g, startAA, endAA);
                    results.add(dto);
                }
            }
            if (!found) {
                MappingResultDto dto = new MappingResultDto();
                dto.setPeptide(pep);
                results.add(dto);
            }
        }

        return new MappingResponseDto("Mapping completed", results);
    }

    private MappingResultDto createMappingDto(String pep, ProteinInfo info, GFFRecord g, int startAA, int endAA) {
        MappingResultDto dto = new MappingResultDto();
        dto.setPeptide(pep);
        dto.setTranscriptId(info.transcriptId);
        dto.setGeneId(info.geneId);
        dto.setChrom(info.chrom);
        dto.setPeptideStart(startAA);
        dto.setPeptideEnd(endAA);

        // ✅ Include description in the response DTO
        dto.setDescription(info.description);

        if (g != null) {
            dto.setGeneStart(g.start);
            dto.setGeneEnd(g.end);
            dto.setStrand(g.strand);
            if (g.strand.equals("+")) {
                dto.setGenomicStart(g.start + (startAA - 1) * 3);
                dto.setGenomicEnd(g.start + (endAA * 3) - 1);
            } else {
                dto.setGenomicStart(g.end - (endAA * 3) + 1);
                dto.setGenomicEnd(g.end - (startAA - 1) * 3);
            }
        }
        return dto;
    }

    // === Helper inner classes ===
    static class ProteinInfo {
        String transcriptId, geneId, chrom, sequence, description;
        ProteinInfo(String t, String g, String c, String s, String d) {
            transcriptId = t; geneId = g; chrom = c; sequence = s; description = d;
        }

        @Override
        public String toString() {
            return "ProteinInfo{" +
                    "transcriptId='" + transcriptId + '\'' +
                    ", geneId='" + geneId + '\'' +
                    ", chrom='" + chrom + '\'' +
                    ", description='" + description + '\'' +
                    ", seqLen=" + (sequence != null ? sequence.length() : 0) +
                    '}';
        }
    }

    static class GFFRecord {
        String chrom, strand, transcriptId, geneId, note;
        int start, end;
        GFFRecord(String c, int s, int e, String st, String t, String g, String n) {
            chrom = c; start = s; end = e; strand = st; transcriptId = t; geneId = g; note = n;
        }
    }

    private Map<String, GFFRecord> loadGFF(String gffFile) throws IOException {
        Map<String, GFFRecord> gffMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(gffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;
                String[] f = line.split("\t");
                if (f.length < 9 || !f[2].equals("mRNA")) continue;
                String transcriptId = extractAttr(f[8], "ID");
                String locus = extractAttr(f[8], "Locus_id");
                String note = extractAttr(f[8], "Note");
                gffMap.put(transcriptId,
                        new GFFRecord(f[0], Integer.parseInt(f[3]), Integer.parseInt(f[4]),
                                f[6], transcriptId, locus, note));
            }
        }
        return gffMap;
    }

    private String extractAttr(String attr, String key) {
        Pattern p = Pattern.compile(key + "=([^;]+)");
        Matcher m = p.matcher(attr);
        return m.find() ? m.group(1) : "";
    }
}
