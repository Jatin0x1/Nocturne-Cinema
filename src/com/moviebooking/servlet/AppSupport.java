package com.moviebooking.servlet;

import com.moviebooking.service.BookingService;
import com.moviebooking.service.MovieCatalog;
import javax.servlet.ServletContext;

final class AppSupport {
    private AppSupport() { }
    static MovieCatalog catalog(ServletContext context) {
        synchronized (context) {
            MovieCatalog catalog = (MovieCatalog) context.getAttribute("movieCatalog");
            if (catalog == null) { catalog = new MovieCatalog(); context.setAttribute("movieCatalog", catalog); }
            return catalog;
        }
    }
    static BookingService bookings(ServletContext context) {
        synchronized (context) {
            BookingService service = (BookingService) context.getAttribute("bookingService");
            if (service == null) { service = new BookingService(); context.setAttribute("bookingService", service); }
            return service;
        }
    }
    static String base(javax.servlet.http.HttpServletRequest request) { return request.getContextPath(); }
}
