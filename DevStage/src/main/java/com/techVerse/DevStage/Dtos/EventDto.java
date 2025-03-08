package com.techVerse.DevStage.Dtos;

import com.techVerse.DevStage.Entities.Event;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDto {

    @Column(name = "event_id")
    private Integer eventId;

    @NotBlank(message = "O título é obrigatório")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "pretty_name",length = 50, nullable = false, unique = true)
    private String prettyName;

    @NotBlank(message = "A localização é obrigatória")
    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @NotBlank(message = "O preço é obrigatório")
    @Column(name = "price", length = 255, nullable = false)
    private Double price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public EventDto() {}

    public EventDto(Integer eventId, String title, String prettyName, String location, Double price, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.eventId = eventId;
        this.title = title;
        this.prettyName = prettyName;
        this.location = location;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventDto(Event event){
        eventId = event.getEventId();
        title = event.getTitle();
        prettyName = event.getPrettyName();
        location = event.getLocation();
        price = event.getPrice();
        startDate = event.getStartDate();
        endDate = event.getEndDate();
        startTime = event.getStartTime();
        endTime = event.getEndTime();

    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
}
