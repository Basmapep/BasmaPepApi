package net.javaguides.peptides_backend.dto;

import lombok.Data;

@Data
public class MappingResultDto {
    private String peptide;
    private String transcriptId;
    private String geneId;
    private String chrom;
    private int peptideStart;
    private int peptideEnd;
    private int geneStart;
    private int geneEnd;
    private String strand;
    private int genomicStart;
    private int genomicEnd;

    // getters & setters
}
