package com.moviebooking.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyBookingsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookings", AppSupport.bookings(getServletContext()).getBookings(request.getSession()));
        Object latest = request.getSession().getAttribute("latestBooking");
        if (latest != null && "1".equals(request.getParameter("confirmed"))) request.setAttribute("confirmedBooking", latest);
        request.getRequestDispatcher("/my-bookings.jsp").forward(request, response);
    }
}
