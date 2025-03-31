package com.coworking.menus;

import com.coworking.dto.ReservationRequest;
import com.coworking.models.CoworkingSpace;
import com.coworking.models.Reservation;
import com.coworking.services.ReservationService;
import com.coworking.services.SpaceService;
import com.coworking.exceptions.ReservationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserMenu {
    private final SpaceService spaceService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public UserMenu(SpaceService spaceService, ReservationService reservationService) {
        this.spaceService = spaceService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel reservation");
            System.out.println("5. Return to main menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    browseSpaces();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewMyReservations();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void browseSpaces() {
        List<CoworkingSpace> availableSpaces = spaceService.getAvailableSpaces();
        if (availableSpaces.isEmpty()) {
            System.out.println("No available spaces found.");
        } else {
            System.out.println("\nAvailable Coworking Spaces:");
            availableSpaces.forEach(space ->
                    System.out.printf("ID: %d, Type: %s, Price: $%.2f/hour%n",
                            space.getId(),
                            space.getType(),
                            space.getPrice())
            );
        }
    }

    private void makeReservation() {
        browseSpaces();
        System.out.print("Enter the space ID you want to book: ");
        Long spaceId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<CoworkingSpace> spaceOpt = spaceService.getSpaceById(spaceId);
        if (spaceOpt.isEmpty()) {
            System.out.println("Invalid space ID.");
            return;
        }

        CoworkingSpace space = spaceOpt.get();
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter the date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.print("Enter the start time (HH:MM): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
        System.out.print("Enter the end time (HH:MM): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        try {
            ReservationRequest request = new ReservationRequest(
                    space, customerName, date, startTime, endTime
            );

            Optional<Reservation> reservation = reservationService.makeReservation(request);
            reservation.ifPresent(r ->
                    System.out.println("Reservation successful! Your reservation ID is " + r.getId() + ".")
            );
        } catch (ReservationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void viewMyReservations() {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        List<Reservation> userReservations = reservationService.getReservationsByCustomer(customerName);

        if (userReservations.isEmpty()) {
            System.out.println("No reservations found for you.");
        } else {
            System.out.println("\nYour Reservations:");
            userReservations.forEach(reservation ->
                    System.out.printf("ID: %d, Space: %s, Date: %s, Time: %s-%s%n",
                            reservation.getId(),
                            reservation.getSpace().getType(),
                            reservation.getDate(),
                            reservation.getStartTime(),
                            reservation.getEndTime())
            );
        }
    }

    private void cancelReservation() {
        System.out.print("Enter the reservation ID to cancel: ");
        Long reservationId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            reservationService.cancelReservation(reservationId);
            System.out.println("Reservation " + reservationId + " canceled successfully.");
        } catch (ReservationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}