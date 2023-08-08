package com.example.CalScheduler.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CalScheduler.model.MeetingEventModel;
import com.example.CalScheduler.service.MeetingService;
import com.opencsv.CSVReader;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private MeetingService meetingService;
	
	@GetMapping
    public String showUploadForm() {
        return "upload"; // This maps to the upload.html template
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception{
        System.out.println("Received file: " + file.getOriginalFilename());
        
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                MeetingEventModel event = new MeetingEventModel();
                event.setTopicDescription(nextRecord[0]);
                event.setDate(LocalDate.parse(nextRecord[1]));
                event.setStartTime(LocalTime.parse(nextRecord[2]));
                event.setEndTime(LocalTime.parse(nextRecord[3]));
                event.setInvitees(Arrays.asList(nextRecord[4].split(",")));

                meetingService.scheduleMeetingAndSendInvitations(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        
        return "redirect:/upload";
    }
}
