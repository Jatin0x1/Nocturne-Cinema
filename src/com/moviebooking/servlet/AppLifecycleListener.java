package com.moviebooking.servlet;

import com.moviebooking.service.BookingService;
import com.moviebooking.service.MovieCatalog;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** Creates fresh application-scoped catalog and seat state for each deployment. */
public class AppLifecycleListener implements ServletContextListener {
    @Override public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        // Clear attributes that a container may retain when it reloads a context.
        context.removeAttribute("movieCatalog");
        context.removeAttribute("bookingService");
        context.setAttribute("movieCatalog", new MovieCatalog());
        context.setAttribute("bookingService", new BookingService());
    }

    @Override public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute("movieCatalog");
        context.removeAttribute("bookingService");
    }
}
