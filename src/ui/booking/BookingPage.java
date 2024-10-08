package ui.booking;

import java.util.Scanner;

import account.UserAccount;
import booking.BookingController;
import color.Color;
import util.CommonIcon;

import java.util.ArrayList;

public class BookingPage implements Page {
    private ArrayList<UserAccount> users;
    private int user_index;
    private Scanner scanner;
    private BookingController bookingController;

    /**
     * Constructor for BookingPage for a user
     * 
     * @param user
     * @param scanner
     */
    public BookingPage(ArrayList<UserAccount> users, int user_index, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
        this.user_index = user_index;
        this.bookingController = new BookingController(users, user_index);
    }

    /**
     * Displays the booking page
     * 
     * @return 0 if no errors occur
     */
    public void display() {
        CommonIcon.printHeader();
        System.out.println();
        System.out.println(Color.RESET + "Welcome, " + users.get(user_index).getName() + "!\n");

        // Show booking menu
        getChoice();
        return;
    }

    /**
     * Gets user choice for booking
     * 
     * @throws IllegalArgumentException
     */
    public void getChoice() {
        int choice = 0;
        boolean validInput = false;
        while (!validInput || choice != 4) {
            System.out.println(Color.RESET + "Your Bookings to attend:");

            // Prints all booking details
            bookingController.printAllOngoingBookings();

            // Print booking menu
            System.out.println(Color.RESET + "What would you like to do with your bookings?");
            System.out.println(Color.RED + "0) " + Color.LIME + "Back to Main Menu");
            System.out.println(Color.RED + "1) " + Color.LIME + "Create Bookings");
            System.out.println(Color.RED + "2) " + Color.LIME + "Update Bookings");
            System.out.println(Color.RED + "3) " + Color.LIME + "Delete Bookings");
            System.out.println(Color.RED + "4) " + Color.LIME + "Search Bookings");
            System.out.println();
            System.out.print(Color.RESET + "Enter your choice: ");
            if (scanner.hasNextInt()) { // Check if input is an integer
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (choice < 0 || choice > 4) {
                    System.out.println(
                            Color.RED + "Invalid choice. Please enter a number between 1 and 4."
                                    + Color.RESET);
                    continue;
                }
                validInput = true;
            } else {
                System.out
                        .println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
                scanner.next(); // Discard the invalid input
                continue;
            }

            // Switch statements
            try {
                switch (choice) {
                    case 0: // Back to main menu
                        System.out.println(Color.RESET + "Returning to main menu...");
                        return;
                    case 1: // Create bookings
                        CreateBookingPage createBookingPage =
                                new CreateBookingPage(bookingController, scanner);
                        createBookingPage.display();
                        break;
                    case 2: // Update bookings
                        UpdateBookingPage updateBookingPage =
                                new UpdateBookingPage(bookingController, scanner);
                        updateBookingPage.display();
                        break;
                    case 3: // Delete bookings
                        DeleteBookingPage deleteBookingPage =
                                new DeleteBookingPage(bookingController, scanner);
                        deleteBookingPage.display();
                        break;
                    case 4: // Search bookings
                        String query = getSearchInput();
                        bookingController.searchBookings(query);
                        util.Util.waitForEnter(scanner);
                        return;
                    default:
                        System.out.println(
                                Color.RED + "Invalid choice. Please enter a number between 1 and 4."
                                        + Color.RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(
                        Color.RED + "An unexpected error occured:" + e.getMessage() + Color.RESET);
                getChoice();
            } finally {
                validInput = false;
                choice = 0;
            }
        }
    }

    /**
     * Gets search input from user
     */
    public String getSearchInput() {
        boolean validInput = false;
        String query = "";
        while (!validInput) {
            System.out.println("Search Bookings");
            System.out.print(Color.RESET + "Enter movie title of the booking: ");
            if (scanner.hasNextLine()) {
                query = scanner.nextLine();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a string.");
                scanner.next(); // Discard the invalid input
                continue;
            }
        }
        return query;
    }
}
