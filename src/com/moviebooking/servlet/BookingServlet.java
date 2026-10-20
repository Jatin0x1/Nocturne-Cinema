package com.moviebooking.servlet;

import com.moviebooking.model.Booking;
import com.moviebooking.model.Movie;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookingServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/movies");
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("movieId");
        Movie movie = AppSupport.catalog(getServletContext()).findById(id);
        try {
            Booking booking = AppSupport.bookings(getServletContext()).createBooking(request.getSession(), movie, request.getParameter("date"), request.getParameter("time"), request.getParameterValues("seats"));
            request.getSession().setAttribute("latestBooking", booking);
            response.sendRedirect(request.getContextPath() + "/bookings?confirmed=1");
        } catch (IllegalArgumentException | ArithmeticException ex) {
            request.setAttribute("errorMessage", html(ex.getMessage() == null ? "We could not complete that booking. Please try again." : ex.getMessage()));
            if (movie != null) {
                request.setAttribute("movie", movie);
                request.setAttribute("showtimes", AppSupport.bookings(getServletContext()).getShowtimes());
                request.setAttribute("seats", AppSupport.bookings(getServletContext()).getSeats());
                request.setAttribute("selectedDate", html(request.getParameter("date") == null ? "" : request.getParameter("date")));
                request.setAttribute("selectedTime", request.getParameter("time"));
                request.setAttribute("bookedSeats", bookedSeats(movie.getId(), request));
                request.getRequestDispatcher("/movie-details.jsp").forward(request, response);
            } else {
                response.setStatus(400); request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }
    private java.util.List<String> bookedSeats(String movieId, HttpServletRequest request) {
        return new java.util.ArrayList<>(AppSupport.bookings(getServletContext()).getBookedSeats(movieId, request.getParameter("date"), request.getParameter("time")));
    }
    private String html(String value) { return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;"); }
}
