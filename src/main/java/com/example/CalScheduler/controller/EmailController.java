package com.example.CalScheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CalScheduler.service.EmailService;

@Controller
@RestController
public class EmailController {
	private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sendEmail")
    public void sendEmail() {
        emailService.sendEmail("springtest001@yopmail.com", "invitation", "Hello");
    }
}
