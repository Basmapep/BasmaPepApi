package net.javaguides.peptides_backend.dto;

import java.util.List;

public class FileDataRequest {
    private List<MailRequest> fileData;
    private String message;  // Add the message field here

    // Getters and setters
    public List<MailRequest> getFileData() {
        return fileData;
    }

    public void setFileData(List<MailRequest> fileData) {
        this.fileData = fileData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
