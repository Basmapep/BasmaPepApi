package net.javaguides.peptides_backend.repository;

import net.javaguides.peptides_backend.dto.PeptideChartData;
import net.javaguides.peptides_backend.dto.peptideDto;
import net.javaguides.peptides_backend.entity.peptide_1121_r1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeptideRepository  extends JpaRepository <peptide_1121_r1  , String> {

    @Query(value = "select a from peptide_1121_r1 a where a.accession like concat('%', ?1, '%')")
    List<peptide_1121_r1> findByAccession(String param,String variety);

    @Query(value = "select a.peptideSeq from peptide_1121_r1 a where a.accession like ?1")
    List<String> findByAccession(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a")
    List<peptide_1121_r1> findByScore(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a where cast(a.avgMass as string) like concat('%', ?1, '%')")
    List<peptide_1121_r1> findByPeptideMass(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a where cast(a.peptideSeqLength as string) like concat('%', ?1, '%')")
    List<peptide_1121_r1> findByPeptideLength(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a where a.peptideModification like ?1")
    List<peptide_1121_r1> findByPeptideModification(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a where a.peptideSeq like ?1")
    List<peptide_1121_r1> findByPeptideSequence(String param);

    @Query(value = "select distinct a from peptide_1121_r1 a where a.peptideSeq like ?1")
    List<peptide_1121_r1> blast(String param);

 /*   @Query(value = "SELECT 'Score Range' AS category, CASE WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 0 AND 1000 THEN '0-1000' WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 1001 AND 2000 THEN '1001-2000' WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 2001 AND 3000 THEN '2001-3000' WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 3001 AND 4000 THEN '3001-4000' WHEN CAST(\"1121_r1_score\" AS NUMERIC) BETWEEN 4001 AND 5000 THEN '4001-5000' ELSE 'Above 5000' END AS range, SUM(CAST(\"1121_r1_score\" AS NUMERIC)) AS total_sum FROM (SELECT DISTINCT CAST(\"1121_r1_score\" AS NUMERIC) FROM \"1121_r1\" WHERE CAST(\"1121_r1_score\" AS NUMERIC) <= 5000) AS t GROUP BY range UNION ALL SELECT 'Peptide Length Range' AS category, CASE WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 0 AND 20 THEN '0-20' WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 21 AND 40 THEN '21-40' WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 41 AND 60 THEN '41-60' WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 61 AND 80 THEN '61-80' WHEN CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) BETWEEN 81 AND 100 THEN '81-100' ELSE 'Above 100' END AS range, SUM(CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC)) AS total_sum FROM (SELECT DISTINCT CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) FROM \"1121_r1\" WHERE CAST(\"1121_r1_peptide_seq_length\" AS NUMERIC) <= 100) AS t GROUP BY range ORDER BY category, range")
    List<peptideDto> bieChart(String param);*/



}
