package com.coworking.menus;

import com.coworking.models.CoworkingSpace;
import com.coworking.models.Reservation;
import com.coworking.services.ReservationService;
import com.coworking.services.SpaceService;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final SpaceService spaceService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public AdminMenu(SpaceService spaceService, ReservationService reservationService) {
        this.spaceService = spaceService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. Remove a coworking space");
            System.out.println("3. View all reservations");
            System.out.println("4. View spaces by type");
            System.out.println("5. Return to main menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addSpace();
                    break;
                case 2:
                    removeSpace();
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    viewSpacesByType();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addSpace() {
        System.out.print("Enter space ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter space type (open space, private, room, etc.): ");
        String type = scanner.nextLine();
        System.out.print("Enter price per hour: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        CoworkingSpace space = new CoworkingSpace(id, type, price);
        spaceService.addSpace(space);
        System.out.println("Space " + id + " added successfully!");
    }

    private void removeSpace() {
        System.out.print("Enter space ID to remove: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        spaceService.removeSpace(id);
        System.out.println("Space " + id + " removed successfully!");
    }

    private void viewAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("\nAll Reservations:");
            reservations.forEach(reservation ->
                    System.out.printf("ID: %d, Space: %s, Customer: %s, Date: %s, Time: %s-%s%n",
                            reservation.getId(),
                            reservation.getSpace().getType(),
                            reservation.getCustomerName(),
                            reservation.getDate(),
                            reservation.getStartTime(),
                            reservation.getEndTime())
            );
        }
    }

    private void viewSpacesByType() {
        System.out.print("Enter space type to filter: ");
        String type = scanner.nextLine();
        List<CoworkingSpace> spaces = spaceService.findSpacesByType(type);

        if (spaces.isEmpty()) {
            System.out.println("No spaces found of type: " + type);
        } else {
            System.out.println("\nSpaces of type " + type + ":");
            spaces.forEach(space ->
                    System.out.printf("ID: %d, Type: %s, Price: $%.2f/hour%n",
                            space.getId(),
                            space.getType(),
                            space.getPrice())
            );
        }
    }
}