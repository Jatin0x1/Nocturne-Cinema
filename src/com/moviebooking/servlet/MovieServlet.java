package com.moviebooking.servlet;

import com.moviebooking.model.Movie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MovieServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && request.getServletPath().endsWith("/details")) { showMovieProfile(request, response, id); return; }
        if (id != null) { showBooking(request, response, id); return; }
        List<Movie> all = AppSupport.catalog(getServletContext()).getAllMovies();
        String q = safe(request.getParameter("q"));
        String genre = safe(request.getParameter("genre"));
        String language = safe(request.getParameter("language"));
        String sort = safe(request.getParameter("sort"));
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : all) {
            if (!q.isEmpty() && !movie.getTitle().toLowerCase().contains(q.toLowerCase())) continue;
            if (!genre.isEmpty() && !genre.equals(movie.getGenre())) continue;
            if (!language.isEmpty() && !language.equals(movie.getLanguage())) continue;
            filtered.add(movie);
        }
        if ("price".equals(sort)) filtered.sort(Comparator.comparingInt(Movie::getTicketPrice));
        else if ("title".equals(sort)) filtered.sort(Comparator.comparing(Movie::getTitle));
        Set<String> genres = new LinkedHashSet<>(), languages = new LinkedHashSet<>();
        for (Movie movie : all) { genres.add(movie.getGenre()); languages.add(movie.getLanguage()); }
        request.setAttribute("movies", filtered);
        request.setAttribute("genres", new ArrayList<>(genres));
        request.setAttribute("languages", new ArrayList<>(languages));
        request.setAttribute("q", html(q)); request.setAttribute("genre", genre); request.setAttribute("language", language); request.setAttribute("sort", sort);
        request.getRequestDispatcher("/movies.jsp").forward(request, response);
    }

    private void showMovieProfile(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        Movie movie = AppSupport.catalog(getServletContext()).findById(id);
        if (movie == null) { response.setStatus(404); request.setAttribute("movieNotFound", Boolean.TRUE); request.setAttribute("errorMessage", "That title is not in the collection. Browse the films and choose another story."); request.getRequestDispatcher("/error.jsp").forward(request, response); return; }
        List<Movie> recommendations = new ArrayList<>();
        for (Movie candidate : AppSupport.catalog(getServletContext()).getAllMovies()) {
            if (!candidate.getId().equals(movie.getId()) && recommendations.size() < 4) recommendations.add(candidate);
        }
        request.setAttribute("movie", movie);
        request.setAttribute("recommendations", recommendations);
        request.getRequestDispatcher("/movie-profile.jsp").forward(request, response);
    }

    private void showBooking(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        Movie movie = AppSupport.catalog(getServletContext()).findById(id);
        if (movie == null) { response.setStatus(404); request.setAttribute("errorMessage", "We couldn't find that movie. Browse the current collection and choose another title."); request.getRequestDispatcher("/error.jsp").forward(request, response); return; }
        request.setAttribute("movie", movie);
        request.setAttribute("showtimes", AppSupport.bookings(getServletContext()).getShowtimes());
        request.setAttribute("seats", AppSupport.bookings(getServletContext()).getSeats());
        request.setAttribute("selectedDate", html(safe(request.getParameter("date"))));
        request.setAttribute("selectedTime", safe(request.getParameter("time")));
        request.setAttribute("bookedSeats", bookedSeats(movie.getId(), request));
        request.getRequestDispatcher("/movie-details.jsp").forward(request, response);
    }
    private List<String> bookedSeats(String movieId, HttpServletRequest request) {
        return new ArrayList<>(AppSupport.bookings(getServletContext()).getBookedSeats(movieId, request.getParameter("date"), request.getParameter("time")));
    }
    private String safe(String value) { return value == null ? "" : value.trim(); }
    private String html(String value) { return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;"); }
}
