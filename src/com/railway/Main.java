package com.railway;

import com.railway.exceptions.BookingException;
import com.railway.models.Passenger;
import com.railway.models.SeatClass;
import com.railway.models.Ticket;
import com.railway.models.Train;
import com.railway.repository.TrainRepository;
import com.railway.service.ReservationService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point providing the Console User Interface.
 */
public class Main {
    public static void main(String[] args) {
        TrainRepository repository = new TrainRepository();
        ReservationService service = new ReservationService(repository);
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("  Advanced Railway Reservation System");
        System.out.println("======================================");

        boolean running = true;

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. View Available Trains");
            System.out.println("2. Book a Ticket");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear bad input
                continue;
            }

            switch (choice) {
                case 1:
                    displayTrains(repository.getAllTrains());
                    break;
                case 2:
                    handleBooking(scanner, service);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
            }
        }
        scanner.close();
    }

    private static void displayTrains(List<Train> trains) {
        if (trains.isEmpty()) {
            System.out.println("No trains currently available in the database.");
            return;
        }

        System.out.println("\n--- Available Trains ---");
        for (Train train : trains) {
            System.out.println(train);
            System.out.printf("   [Seats] SLEEPER: %d | AC: %d | GENERAL: %d\n",
                    train.getAvailableSeats(SeatClass.SLEEPER),
                    train.getAvailableSeats(SeatClass.AC),
                    train.getAvailableSeats(SeatClass.GENERAL));
        }
        System.out.println("------------------------");
    }

    private static void handleBooking(Scanner scanner, ReservationService service) {
        try {
            System.out.print("Enter Train Number: ");
            String trainNumber = scanner.nextLine().trim();

            System.out.print("Enter Passenger Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Passenger Age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Select Seat Class (1. SLEEPER, 2. AC, 3. GENERAL): ");
            int classChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            SeatClass seatClass;
            if (classChoice == 1) seatClass = SeatClass.SLEEPER;
            else if (classChoice == 2) seatClass = SeatClass.AC;
            else if (classChoice == 3) seatClass = SeatClass.GENERAL;
            else {
                System.out.println("Invalid class selection.");
                return;
            }

            System.out.print("Enter Journey Date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine().trim();
            LocalDate journeyDate = LocalDate.parse(dateStr);

            Passenger passenger = new Passenger(name, age);
            Ticket ticket = service.bookTicket(trainNumber, passenger, seatClass, journeyDate);

            System.out.println("\n*** Booking Successful! ***");
            System.out.println(ticket);

        } catch (InputMismatchException e) {
            System.out.println("Invalid data type entered. Booking aborted.");
            scanner.nextLine(); // clear buffer
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD. Booking aborted.");
        } catch (BookingException e) {
            System.out.println("\nBooking Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
