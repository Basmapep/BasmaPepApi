package net.javaguides.peptides_backend.service;

import net.javaguides.peptides_backend.dto.UserDetailsBean;
import java.util.List;

public interface UserDetailsService {
    String createNewUser(UserDetailsBean userDetailsBean);
    String checkCredentials(String userName, String password);

    // Modify sendMail method to accept lists of base64 files and filenames
    String sendMail(String userId, List<String> base64Files, List<String> filenames, String messageContent);
}
