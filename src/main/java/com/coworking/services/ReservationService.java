package com.coworking.services;

import com.coworking.dto.ReservationRequest;
import com.coworking.models.Reservation;
import com.coworking.exceptions.ReservationException;
import com.coworking.repositories.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Optional<Reservation> makeReservation(ReservationRequest request) throws ReservationException {
        return reservationRepository.getNextReservationId()
                .map(id -> {
                    Reservation reservation = new Reservation(
                            id,
                            request.getSpace(),
                            request.getCustomerName(),
                            request.getDate(),
                            request.getStartTime(),
                            request.getEndTime()
                    );

                    if (reservationRepository.hasConflictingReservation(reservation)) {
                        try {
                            throw new ReservationException("Time conflict with existing reservation");
                        } catch (ReservationException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    reservationRepository.addReservation(reservation);
                    return reservation;
                });
    }

    public void cancelReservation(Long id) throws ReservationException {
        if (reservationRepository.getReservationById(id).isEmpty()) {
            throw new ReservationException("Reservation not found");
        }
        reservationRepository.deleteReservation(id);
    }

    public List<Reservation> getReservationsByCustomer(String customerName) {
        return reservationRepository.getReservationsByCustomer(customerName);
    }

    public Map<LocalDate, List<Reservation>> getReservationsByDate() {
        return reservationRepository.getReservationsByDate();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }
}