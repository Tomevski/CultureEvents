package com.example.cultureevents.models;

public class Event {
    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String image;
    private String creationDate;
    private Boolean neededTicket;

    public Event(String name, String description, String location, String date, String time, String creationDate, String image, Boolean neededTicket) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.time=time;
        this.image=image;
        this.creationDate=creationDate;
        this.neededTicket = neededTicket;
    }

    public String getName() {
        return name;
    }
}
