package com.example.CalScheduler.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventAttendee;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CalScheduler.model.MeetingEventModel;

@Service
public class CalendarService {

    private static final String APPLICATION_NAME = "CalSchedulingSystem";
    private static final JsonFactory  JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CALENDAR_ID = "primary"; 

    @Autowired
    private GoogleCredentialsService googleCredentialsService; // You need to implement this

    public Event createCalendarEvent(MeetingEventModel meetingEventModel,  String authorizationCode) throws IOException, GeneralSecurityException {
        Credential credential = googleCredentialsService.getCredentials(authorizationCode); // Obtain OAuth 2.0 credentials

        Calendar service = new Calendar.Builder(
            GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
        
     // Convert LocalTime and LocalDate to DateTime
        LocalDateTime startDateTime = LocalDateTime.of(meetingEventModel.getDate(), meetingEventModel.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(meetingEventModel.getDate(), meetingEventModel.getEndTime());
        ZoneId zoneId = ZoneId.systemDefault();
        long startepochMillis = startDateTime.atZone(zoneId).toInstant().toEpochMilli();
        long endepochMillis = endDateTime.atZone(zoneId).toInstant().toEpochMilli();
        
        DateTime startTime = new DateTime(startepochMillis);
        DateTime endTime = new DateTime(endepochMillis)
;        
        
        Event event = new Event()
            .setSummary(meetingEventModel.getTopicDescription())
            .setStart(new EventDateTime().setDateTime(startTime))
            .setEnd(new EventDateTime().setDateTime(endTime));

        // Add attendees
        List<EventAttendee> attendees = new ArrayList<>();
        for (String email : meetingEventModel.getInvitees()) {
            attendees.add(new EventAttendee().setEmail(email));
        }
        event.setAttendees(attendees);

        event = service.events().insert(CALENDAR_ID, event).execute();
        return event;
    }
}
