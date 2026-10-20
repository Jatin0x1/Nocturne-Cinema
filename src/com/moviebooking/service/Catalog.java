package com.moviebooking.service;

import com.moviebooking.model.Movie;
import java.util.List;

public interface Catalog {
    List<Movie> getAllMovies();
    Movie findById(String id);
}
