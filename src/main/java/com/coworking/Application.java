package com.coworking;

import com.coworking.menus.AdminMenu;
import com.coworking.menus.UserMenu;
import com.coworking.services.ReservationService;
import com.coworking.services.SpaceService;
import java.util.Scanner;

public class Application {
    private final AdminMenu adminMenu;
    private final UserMenu userMenu;
    private final Scanner scanner;

    public Application(AdminMenu adminMenu, UserMenu userMenu) {
        this.adminMenu = adminMenu;
        this.userMenu = userMenu;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nWelcome to Coworking Space Reservation System!");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu.showMenu();
                    break;
                case 2:
                    userMenu.showMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}