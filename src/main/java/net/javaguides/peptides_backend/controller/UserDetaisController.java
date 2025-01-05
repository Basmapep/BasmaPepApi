package net.javaguides.peptides_backend.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.javaguides.peptides_backend.dto.FileDataRequest;
import net.javaguides.peptides_backend.dto.MailRequest;
import net.javaguides.peptides_backend.dto.UserDetailsBean;
import net.javaguides.peptides_backend.service.UserDetailsService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
@CrossOrigin
@RequestMapping(value = "/api/UserDetails")
public class UserDetaisController {

    @Autowired
    UserDetailsService userDetailsService;

    @ResponseBody
    @RequestMapping(value = "/createNewUser",method = RequestMethod.POST)
    public String createNewUser(@RequestBody UserDetailsBean userDetailsBean){
        return userDetailsService.createNewUser(userDetailsBean);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password){
        return userDetailsService.checkCredentials(userName,password);
    }



    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public String sendMailWithAttachment(@RequestBody FileDataRequest fileDataRequest) {
        // Extract the userId from the first element in the fileData list
        String userId = fileDataRequest.getFileData().get(0).getUserId();

        // Extract the message content from the request
        String messageContent = fileDataRequest.getMessage(); // Assuming "message" is a field in FileDataRequest

        // Create lists to hold file paths and filenames
        List<String> files = new ArrayList<>();
        List<String> filenames = new ArrayList<>();

        // Loop through the fileData and extract files and filenames
        for (MailRequest data : fileDataRequest.getFileData()) {
            files.add(data.getFiles());
            filenames.add(data.getFilenames());
        }

        // Pass the extracted information to the service
        return userDetailsService.sendMail(userId, files, filenames, messageContent);
    }



}



