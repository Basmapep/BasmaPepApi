package net.javaguides.peptides_backend.dto;

import java.util.List;

public class MappingResponseDto {
    private String message;
    private List<MappingResultDto> results;

    public MappingResponseDto(String message, List<MappingResultDto> results) {
        this.message = message;
        this.results = results;
    }

    public String getMessage() { return message; }
    public List<MappingResultDto> getResults() { return results; }

    public void setMessage(String message) { this.message = message; }
    public void setResults(List<MappingResultDto> results) { this.results = results; }
}
