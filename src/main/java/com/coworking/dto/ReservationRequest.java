package com.coworking.dto;

import com.coworking.models.CoworkingSpace;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private CoworkingSpace space;
    private String customerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ReservationRequest(CoworkingSpace space, String customerName,
                              LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.space = space;
        this.customerName = customerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public CoworkingSpace getSpace() { return space; }
    public String getCustomerName() { return customerName; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
}