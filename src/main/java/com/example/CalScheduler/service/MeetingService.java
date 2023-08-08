package com.example.CalScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CalScheduler.model.MeetingEventModel;
import com.google.api.services.calendar.model.Event;

@Service
public class MeetingService {
	
	private String authorizationCode; 

    @Autowired
    private CalendarService calendarService;

//    @Autowired
//    private EmailService emailService;

    public void scheduleMeetingAndSendInvitations(MeetingEventModel meetingEventModel) {
        try {
            // Create the event on Google Calendar
            Event createdEvent = calendarService.createCalendarEvent(meetingEventModel, authorizationCode);

            // Send email invitations to attendees
//            emailService.sendMeetingInvitations(meetingEventModel, createdEvent);

            System.out.println("Meeting scheduled and invitations sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors
        }
    }
}
