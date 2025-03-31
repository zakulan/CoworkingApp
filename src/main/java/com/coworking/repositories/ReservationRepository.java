package com.coworking.repositories;

import com.coworking.models.Reservation;
import com.coworking.persistence.DataStorage;
import com.coworking.exceptions.StorageException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationRepository {
    private final DataStorage<Reservation> storage;
    private List<Reservation> reservations;
    private Long reservationIdCounter = 1L;

    public ReservationRepository(DataStorage<Reservation> storage) {
        this.storage = storage;
        this.reservations = loadReservations();
        initializeIdCounter();
    }

    private List<Reservation> loadReservations() {
        try {
            return storage.load();
        } catch (StorageException e) {
            System.err.println("Error loading reservations: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void initializeIdCounter() {
        reservationIdCounter = reservations.stream()
                .mapToLong(Reservation::getId)
                .max()
                .orElse(0L) + 1L;
    }

    public void saveReservations() {
        try {
            storage.save(reservations);
        } catch (StorageException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }

    public void addReservation(Reservation reservation) {
        reservation.setId(reservationIdCounter++);
        reservations.add(reservation);
        saveReservations();
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        saveReservations();
    }

    public List<Reservation> getReservationsByCustomer(String customerName) {
        return reservations.stream()
                .filter(reservation -> reservation.getCustomerName().equals(customerName))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsForSpace(Long spaceId) {
        return reservations.stream()
                .filter(reservation -> reservation.getSpace().getId().equals(spaceId))
                .sorted(Comparator.comparing(Reservation::getDate)
                        .thenComparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }

    public boolean hasConflictingReservation(Reservation newReservation) {
        return reservations.stream()
                .anyMatch(existing -> isTimeConflict(existing, newReservation));
    }

    private boolean isTimeConflict(Reservation r1, Reservation r2) {
        return r1.getSpace().equals(r2.getSpace()) &&
                r1.getDate().equals(r2.getDate()) &&
                r1.getStartTime().isBefore(r2.getEndTime()) &&
                r1.getEndTime().isAfter(r2.getStartTime());
    }

    public Optional<Long> getNextReservationId() {
        return Optional.of(reservationIdCounter);
    }

    public Map<LocalDate, List<Reservation>> getReservationsByDate() {
        return reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getDate));
    }
}