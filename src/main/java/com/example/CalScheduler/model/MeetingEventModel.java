package com.example.CalScheduler.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MeetingEventModel {
	private String topicDescription;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> invitees;
	public String getTopicDescription() {
		return topicDescription;
	}
	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public List<String> getInvitees() {
		return invitees;
	}
	public void setInvitees(List<String> invitees) {
		this.invitees = invitees;
	}

}
