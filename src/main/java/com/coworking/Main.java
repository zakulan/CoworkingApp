package com.coworking;

import com.coworking.models.CoworkingSpace;
import com.coworking.models.Reservation;
import com.coworking.persistence.*;
import com.coworking.repositories.*;
import com.coworking.services.*;
import com.coworking.menus.*;

public class Main {
    private static final String SPACES_FILE = "spaces.dat";
    private static final String RESERVATIONS_FILE = "reservations.dat";

    public static void main(String[] args) {
        // Initialize storage
        DataStorage<CoworkingSpace> spaceStorage = new CoworkingSpaceStorage(SPACES_FILE);
        DataStorage<Reservation> reservationStorage = new ReservationStorage(RESERVATIONS_FILE);

        // Initialize repositories with storage
        SpaceRepository spaceRepo = new SpaceRepository(spaceStorage);
        ReservationRepository reservationRepo = new ReservationRepository(reservationStorage);

        // Initialize services
        SpaceService spaceService = new SpaceService(spaceRepo, reservationRepo);
        ReservationService reservationService = new ReservationService(reservationRepo);

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            spaceRepo.saveSpaces();
            reservationRepo.saveReservations();
            System.out.println("Application shutdown - data saved");
        }));

        // Start application
        new Application(
                new AdminMenu(spaceService, reservationService),
                new UserMenu(spaceService, reservationService)
        ).run();
    }
}