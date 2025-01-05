package net.javaguides.peptides_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class MailRequest {
    private String userId;
    private String files;
    private String filenames;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }
}
