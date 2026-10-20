package com.moviebooking.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("featuredMovies", AppSupport.catalog(getServletContext()).getAllMovies().subList(0, 4));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
