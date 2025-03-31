package com.coworking.services;

import com.coworking.models.CoworkingSpace;
import com.coworking.models.Reservation;
import com.coworking.repositories.ReservationRepository;
import com.coworking.repositories.SpaceRepository;
import java.util.*;
import java.util.stream.Collectors;

public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    public SpaceService(SpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    public void addSpace(CoworkingSpace space) {
        spaceRepository.addSpace(space);
    }

    public void removeSpace(Long id) {
        spaceRepository.removeSpace(id);
    }

    public List<CoworkingSpace> getAvailableSpaces() {
        List<CoworkingSpace> allSpaces = spaceRepository.getAllSpaces();
        List<Reservation> allReservations = reservationRepository.getAllReservations();

        return allSpaces.stream()
                .filter(space -> allReservations.stream()
                        .noneMatch(reservation -> reservation.getSpace().equals(space)))
                .collect(Collectors.toList());
    }

    public Optional<CoworkingSpace> getSpaceById(Long id) {
        return spaceRepository.getSpaceById(id);
    }

    public List<CoworkingSpace> findSpacesByType(String type) {
        return spaceRepository.findSpacesByType(type);
    }

    public Map<String, List<CoworkingSpace>> groupSpacesByType() {
        return spaceRepository.groupSpacesByType();
    }

    public List<Reservation> getReservationsForSpace(Long spaceId) {
        return reservationRepository.getReservationsForSpace(spaceId);
    }
}