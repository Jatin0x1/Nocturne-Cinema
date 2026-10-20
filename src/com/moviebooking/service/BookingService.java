package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.Movie;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpSession;

public class BookingService {
    private static final String SESSION_BOOKINGS = "movieBookings";
    private static final List<String> SHOWTIMES = Arrays.asList("10:30 AM", "1:45 PM", "5:15 PM", "8:40 PM");
    private static final Set<String> SEATS = new HashSet<>();
    // Shared seat occupancy belongs to the application service, not to a user session.
    // Booking history itself remains private to each visitor's HttpSession.
    private final Map<String, Set<String>> bookedSeatsByShow = new HashMap<>();
    static {
        for (char row = 'A'; row <= 'F'; row++) for (int n = 1; n <= 10; n++) SEATS.add(row + String.valueOf(n));
    }

    @SuppressWarnings("unchecked")
    public List<Booking> getBookings(HttpSession session) {
        List<Booking> bookings = (List<Booking>) session.getAttribute(SESSION_BOOKINGS);
        if (bookings == null) { bookings = new ArrayList<>(); session.setAttribute(SESSION_BOOKINGS, bookings); }
        return bookings;
    }

    public List<String> getShowtimes() { return new ArrayList<>(SHOWTIMES); }
    public Set<String> getSeats() { return new HashSet<>(SEATS); }
    public synchronized Set<String> getBookedSeats(String movieId, String date, String time) {
        Set<String> booked = bookedSeatsByShow.get(showKey(movieId, date, time));
        return booked == null ? new HashSet<>() : new HashSet<>(booked);
    }

    public synchronized Booking createBooking(HttpSession session, Movie movie, String dateText, String time, String[] requestedSeats) {
        if (movie == null) throw new IllegalArgumentException("That movie could not be found. Please choose a movie again.");
        LocalDate date;
        try { date = LocalDate.parse(dateText); }
        catch (RuntimeException ex) { throw new IllegalArgumentException("Choose a valid show date."); }
        if (date.isBefore(LocalDate.now())) throw new IllegalArgumentException("Show date cannot be in the past.");
        if (!SHOWTIMES.contains(time)) throw new IllegalArgumentException("Choose one of the available showtimes.");
        if (requestedSeats == null || requestedSeats.length == 0) throw new IllegalArgumentException("Select at least one seat.");

        Set<String> unique = new HashSet<>();
        for (String seat : requestedSeats) {
            if (seat == null || !SEATS.contains(seat)) throw new IllegalArgumentException("One or more selected seats are invalid.");
            if (!unique.add(seat)) throw new IllegalArgumentException("A seat was submitted more than once.");
        }
        String showKey = showKey(movie.getId(), dateText, time);
        Set<String> alreadyBooked = bookedSeatsByShow.get(showKey);
        if (alreadyBooked != null) for (String seat : unique)
            if (alreadyBooked.contains(seat)) throw new IllegalArgumentException("Seat " + seat + " was just booked. Please select another seat.");
        List<String> selected = new ArrayList<>(unique);
        selected.sort(String::compareTo);
        int total = Math.multiplyExact(movie.getTicketPrice(), selected.size());
        String reference = "NC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Booking booking = new Booking(reference, movie.getTitle(), dateText, time, selected, total);
        getBookings(session).add(booking);
        bookedSeatsByShow.computeIfAbsent(showKey, key -> new HashSet<>()).addAll(selected);
        return booking;
    }

    private String showKey(String movieId, String date, String time) {
        return movieId + "|" + date + "|" + time;
    }
}
