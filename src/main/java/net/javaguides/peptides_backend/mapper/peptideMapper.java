package net.javaguides.peptides_backend.mapper;

import net.javaguides.peptides_backend.dto.peptideDto;
import net.javaguides.peptides_backend.entity.peptide_1121_r1;

public class peptideMapper {

    public static peptideDto mapTopeptideDto (peptide_1121_r1 peptide_1121_r1) {
        return new peptideDto(
                peptide_1121_r1.getEntry(),
                peptide_1121_r1.getAccession(),
                peptide_1121_r1.getDescription(),
                peptide_1121_r1.getScore(),
                peptide_1121_r1.getFalsePositiveRate(),
                peptide_1121_r1.getAvgMass(),
                peptide_1121_r1.getPeptideModification(),
                peptide_1121_r1.getPeptideMhp(),
                peptide_1121_r1.getPeptideSeq(),
                peptide_1121_r1.getPeptideSeqStart(),
                peptide_1121_r1.getPeptideSeqLength(),
                peptide_1121_r1.getPeptideMatchedProducts(),
                peptide_1121_r1.getPrecursorRetentionTime(),
                peptide_1121_r1.getPrecursorIntensity(),
                peptide_1121_r1.getPrecursorCharge(),
                peptide_1121_r1.getPrecursorMz(),
                peptide_1121_r1.getVariety()
        );

    }
}
