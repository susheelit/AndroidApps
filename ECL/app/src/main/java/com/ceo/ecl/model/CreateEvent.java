package com.ceo.ecl.model;

public class CreateEvent {

    public String eventDate;
    public String decription;

    public CreateEvent(String eventDate, String decription) {
        this.eventDate = eventDate;
        this.decription = decription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
}
