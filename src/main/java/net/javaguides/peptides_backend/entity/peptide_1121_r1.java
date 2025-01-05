package net.javaguides.peptides_backend.entity;


import jakarta.persistence.*;
import  lombok.AllArgsConstructor;
import  lombok.Getter;
import  lombok.NoArgsConstructor;
import  lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "1121_r1")
public class peptide_1121_r1 {



    @Column(name = "1121_r1_entry", length = 255)
    private String entry;

    @Id
    @Column(name = "1121_r1_accession", length = 50)
    private String accession;

    @Column(name = "1121_r1_description", columnDefinition = "text")
    private String description;

    @Column(name = "1121_r1_score")
    private String score;

    @Column(name = "1121_r1_false_positive_rate")
    private BigDecimal falsePositiveRate;

    @Column(name = "1121_r1_avg_mass")
    private String avgMass;

    @Column(name = "1121_r1_peptide_modification", length = 255)
    private String peptideModification;

    @Column(name = "1121_r1_peptide_mhp")
    private BigDecimal peptideMhp;

    @Column(name = "1121_r1_peptide_seq", length = 255)
    private String peptideSeq;

    @Column(name = "1121_r1_peptide_seq_start")
    private Integer peptideSeqStart;

    @Column(name = "1121_r1_peptide_seq_length")
    private String peptideSeqLength;

    @Column(name = "1121_r1_peptide_matched_products", columnDefinition = "text")
    private String peptideMatchedProducts;

    @Column(name = "1121_r1_precursor_retention_time")
    private BigDecimal precursorRetentionTime;

    @Column(name = "1121_r1_precursor_intensity")
    private Integer precursorIntensity;

    @Column(name = "1121_r1_precursor_charge")
    private Integer precursorCharge;

    @Column(name = "1121_r1_precursor_mz")
    private BigDecimal precursorMz;


    @Column(name = "1121_r1_variety")
    private String variety;



    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public BigDecimal getFalsePositiveRate() {
        return falsePositiveRate;
    }

    public void setFalsePositiveRate(BigDecimal falsePositiveRate) {
        this.falsePositiveRate = falsePositiveRate;
    }

    public String getAvgMass() {
        return avgMass;
    }

    public void setAvgMass(String avgMass) {
        this.avgMass = avgMass;
    }

    public String getPeptideModification() {
        return peptideModification;
    }

    public void setPeptideModification(String peptideModification) {
        this.peptideModification = peptideModification;
    }

    public BigDecimal getPeptideMhp() {
        return peptideMhp;
    }

    public void setPeptideMhp(BigDecimal peptideMhp) {
        this.peptideMhp = peptideMhp;
    }

    public String getPeptideSeq() {
        return peptideSeq;
    }

    public void setPeptideSeq(String peptideSeq) {
        this.peptideSeq = peptideSeq;
    }

    public Integer getPeptideSeqStart() {
        return peptideSeqStart;
    }

    public void setPeptideSeqStart(Integer peptideSeqStart) {
        this.peptideSeqStart = peptideSeqStart;
    }

    public String getPeptideSeqLength() {
        return peptideSeqLength;
    }

    public void setPeptideSeqLength(String peptideSeqLength) {
        this.peptideSeqLength = peptideSeqLength;
    }

    public String getPeptideMatchedProducts() {
        return peptideMatchedProducts;
    }

    public void setPeptideMatchedProducts(String peptideMatchedProducts) {
        this.peptideMatchedProducts = peptideMatchedProducts;
    }

    public BigDecimal getPrecursorRetentionTime() {
        return precursorRetentionTime;
    }

    public void setPrecursorRetentionTime(BigDecimal precursorRetentionTime) {
        this.precursorRetentionTime = precursorRetentionTime;
    }

    public Integer getPrecursorIntensity() {
        return precursorIntensity;
    }

    public void setPrecursorIntensity(Integer precursorIntensity) {
        this.precursorIntensity = precursorIntensity;
    }

    public Integer getPrecursorCharge() {
        return precursorCharge;
    }

    public void setPrecursorCharge(Integer precursorCharge) {
        this.precursorCharge = precursorCharge;
    }

    public BigDecimal getPrecursorMz() {
        return precursorMz;
    }

    public void setPrecursorMz(BigDecimal precursorMz) {
        this.precursorMz = precursorMz;
    }


    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }
}
