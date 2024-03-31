package booking;

import java.util.ArrayList;

import account.UserAccount;
import cinema.Cinema;
import color.Color;
import movie.Movie;

public class BookingController {
    // Attributes
    private UserAccount user;
    private ArrayList<Booking>bookings = new ArrayList<Booking>();

    /**
     * Constructor for BookingController
     * @param user
     */
    public BookingController(UserAccount user) {
        this.user = user;
        bookings = user.getBookings();
    }

    /**
     * Create and adds a booking to the user's list of bookings
     * @param movie
     * @param cinema
     * @param showtime
     * @param quantityAdult
     * @param quantityChildren
     */
    public void createBooking(
        Movie movie,
        Cinema cinema,
        String showtime,
        int quantityAdult, 
        int quantityChildren
        ) throws IllegalArgumentException
    {
        bookings = user.getBookings();
        Booking booking = new Booking(movie, cinema, showtime, quantityAdult, quantityChildren);
        if (bookings.contains(booking))
        {
            throw new IllegalArgumentException("Booking already exists.");
        }
        bookings.add(booking);
        user.setBookings(bookings);
    }

    /**
     * Updates the booking details
     * @param index is 0-indexed
     * @param movie
     * @param cinema
     * @param showtime
     * @param quantityAdult
     * @param quantityChildren
     */
    public void updateBooking(
        int index,
        Movie movie,
        Cinema cinema,
        String showtime,
        int quantityAdult, 
        int quantityChildren
        )
    {
        bookings = user.getBookings();
        Booking booking = bookings.get(index);
        booking.setMovie(movie);
        booking.setCinema(cinema);
        booking.setShowtime(showtime);
        booking.setQuantityAdult(quantityAdult);
        booking.setQuantityChildren(quantityChildren);
        bookings.set(index, booking);
        user.setBookings(bookings);
    }

    /**
     * Prints the details of a booking
     * @param index is 0-indexed
     * @param booking
     */
    public void printBookingDetails(int index, Booking booking)
    {
        System.out.println(Color.red + (index + 1) + ". \tMovie: " + Color.lime + booking.getMovieName());
        System.out.println(Color.red + "\tShowtime: " + Color.lime + booking.getShowtime());
        System.out.println(Color.red + "\tCinema: " + Color.lime + booking.getCinemaName());
        System.out.println(Color.red + "\tLocation: " + Color.lime + booking.getCinemaAddress());
        System.out.println(Color.red + "\tChildren: " + Color.lime + booking.getQuantityChildren());
        System.out.println(Color.red + "\tAdults: " + Color.lime + booking.getQuantityAdult());
        System.out.println(Color.red + "\tTotal Price: " + Color.lime + "RM " + booking.calculateTotalPrice());
    }

    /**
     * Prints all bookings
     */
    public void printAllBookings()
    {
        for (int i = 0; i < user.getBookings().size(); i++)
        {
            printBookingDetails(i, user.getBookings().get(i));
            System.out.println();
        }
    }

    /**
     * Deletes a booking
     * @param index is 0-indexed
     */
    public void deleteBooking(int index) throws IllegalArgumentException
    {
        bookings = user.getBookings();
        if (index < 0 || index >= bookings.size())
        {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        bookings.remove(index);
        user.setBookings(bookings);
    }
}
