package com.coworking.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private CoworkingSpace space;
    private String customerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Reservation(Long id, CoworkingSpace space, String customerName,
                       LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.space = space;
        this.customerName = customerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CoworkingSpace getSpace() { return space; }
    public void setSpace(CoworkingSpace space) { this.space = space; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(space, that.space) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, space, customerName, date, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Reservation{id=" + id + ", space=" + space + ", customerName='" +
                customerName + "', date=" + date + ", startTime=" + startTime +
                ", endTime=" + endTime + "}";
    }
}