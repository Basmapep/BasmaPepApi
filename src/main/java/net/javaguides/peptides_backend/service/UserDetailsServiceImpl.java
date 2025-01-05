package net.javaguides.peptides_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import net.javaguides.peptides_backend.dto.UserDetailsBean;
import net.javaguides.peptides_backend.entity.UsersDetails;
import net.javaguides.peptides_backend.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    private JavaMailSender javaMailSender; // Autowired to send emails

    @Override
    public String createNewUser(UserDetailsBean userDetailsBean) {
        Map<String, String> response = new HashMap<>();

        if (userDetailsBean != null) {
            String name = userDetailsBean.getUsername();
            List<UsersDetails> userDts = userDetailsRepository.findByUsername(name);

            if (userDts != null && userDts.size() > 0) {
                response.put("status", "exists");
            } else {
                UsersDetails entity = mapUsersDetails(userDetailsBean);
                userDetailsRepository.saveAndFlush(entity);
                response.put("status", "success");
            }
        } else {
            response.put("status", "failure");
        }

        // Convert the map to a JSON string (using your preferred method, e.g., Jackson)
        return "{\"status\": \"" + response.get("status") + "\"}";
    }

    public String checkCredentials(String userName, String password) {
        List<UsersDetails> userDts = userDetailsRepository.findByUsernameAndPasswod(userName, password);

        if (userDts != null && userDts.size() > 0) {
            UsersDetails user = userDts.get(0);  // Get the first user (assuming the list has at least one user)

            // Retrieve all relevant user details
            String userId = String.valueOf(user.getUserId());
            String firstName = String.valueOf(user.getFirstName());
            String lastName = String.valueOf(user.getLastName());
            String email = String.valueOf(user.getEmail());
            String phone = String.valueOf(user.getPhoneNo());
            String address = String.valueOf(user.getAddress());  // Assuming there's an address field

            // Construct a JSON response with all available details
            return String.format("{\"status\": \"success\", \"userId\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": \"%s\", \"address\": \"%s\"}",
                    userId, firstName, lastName, email, phone, address);
        }

        return "{\"status\": \"failure\"}";
    }

    // Modified method to handle multiple attachments
    public String sendMail(String userId, List<String> base64Files, List<String> filenames, String messageContent) {
        try {
            // Step 1: Fetch the user's email address using the userId
            List<UsersDetails> userMail = userDetailsRepository.findByUsernameToSendMail(Integer.valueOf(userId));

            if (userMail != null && !userMail.isEmpty()) {
                String email = userMail.get(0).getEmail(); // Get the email of the user

                // Step 2: Prepare the email
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' for multipart

                // Set the email subject and body
                helper.setTo(email);
                helper.setSubject("Subject of the email");

                // Use the messageContent from the request body as the email content
                helper.setText(messageContent);  // This is the content of the email

                // Step 3: Add multiple attachments
                for (int i = 0; i < base64Files.size(); i++) {
                    String base64File = base64Files.get(i);
                    String filename = filenames.get(i);

                    // Clean the Base64 string by removing the "base64," prefix if it exists
                    if (base64File.contains("base64,")) {
                        base64File = base64File.split("base64,")[1];
                    }

                    // Remove any non-Base64 characters (spaces, newlines, etc.)
                    base64File = base64File.replaceAll("[^A-Za-z0-9+/=]", "");

                    // Decode the Base64 string into a byte array
                    byte[] decodedFile = Base64.getDecoder().decode(base64File);

                    // Create the attachment and add it to the email
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(decodedFile, "application/octet-stream");
                    helper.addAttachment(filename, dataSource);
                }

                // Step 4: Send the email
                javaMailSender.send(message);
                System.out.println("Email sent successfully to: " + email);
                return "{\"status\": \"success\", \"message\": \"Email sent successfully\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"User not found\"}";
            }
        } catch (MessagingException e) {
            return "{\"status\": \"error\", \"message\": \"Failed to send email: " + e.getMessage() + "\"}";
        } catch (IllegalArgumentException e) {
            return "{\"status\": \"error\", \"message\": \"Failed to decode Base64 file: " + e.getMessage() + "\"}";
        } catch (Exception e) {
            return "{\"status\": \"error\", \"message\": \"Failed to send email: " + e.getMessage() + "\"}";
        }
    }


    // Method to map UserDetailsBean to UsersDetails entity
    private UsersDetails mapUsersDetails(UserDetailsBean userDetailsBean) {
        UsersDetails userDetailsEntry = new UsersDetails();
        userDetailsEntry.setFirstName(userDetailsBean.getFirstName());
        userDetailsEntry.setLastName(userDetailsBean.getLastName());
        userDetailsEntry.setAddress(userDetailsBean.getAddress());
        userDetailsEntry.setCity(userDetailsBean.getCity());
        userDetailsEntry.setEmail(userDetailsBean.getEmail());
        userDetailsEntry.setState(userDetailsBean.getState());
        userDetailsEntry.setDateOfBirth(userDetailsBean.getDateOfBirth());
        userDetailsEntry.setPhoneNo(userDetailsBean.getPhoneNo());
        userDetailsEntry.setUsername(userDetailsBean.getUsername());
        userDetailsEntry.setPasswordHash(userDetailsBean.getPasswordHash());
        userDetailsEntry.setZipCode(userDetailsBean.getZipCode());
        userDetailsEntry.setCreatedAt(userDetailsBean.getCreatedOn());
        userDetailsEntry.setUpdatedAt(userDetailsBean.getUpdatedOn());
        return userDetailsEntry;
    }
}
