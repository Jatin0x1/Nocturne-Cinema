package com.moviebooking.service;

import com.moviebooking.model.Movie;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Central in-memory catalogue. Ticket prices are illustrative demo prices in INR. */
public class MovieCatalog implements Catalog {
    private final Map<String, Movie> movies = new LinkedHashMap<>();

    public MovieCatalog() {
        // Poster files are local to web/images; no runtime movie or image API is used.
        add(new Movie("three-idiots", "3 Idiots", "Comedy", 171, "Hindi", "U/A", "Three engineering students challenge rigid expectations and discover what makes a life well lived.", "/images/3-idiots.jpg", 220));
        add(new Movie("dangal", "Dangal", "Drama", 161, "Hindi", "U/A", "A former wrestler trains his daughters to compete at the highest level of the sport.", "/images/dangal.jpg", 240));
        add(new Movie("jawan", "Jawan", "Action", 169, "Hindi", "U/A", "A masked vigilante brings together a team to confront corruption and a powerful arms dealer.", "/images/jawan.jpg", 280));
        add(new Movie("pathaan", "Pathaan", "Action", 146, "Hindi", "U/A", "An exiled field agent returns to stop a mercenary threat against the country.", "/images/pathaan.jpg", 280));
        add(new Movie("rrr", "RRR", "Action", 182, "Telugu", "U/A", "Two very different men form an extraordinary friendship in a fictional story set in colonial India.", "/images/rrr.jpg", 260));
        add(new Movie("baahubali", "Baahubali: The Beginning", "Action", 158, "Telugu", "U/A", "A young man discovers his heritage and becomes part of a struggle for a kingdom.", "/images/baahubali-the-beginning.jpg", 250));
        add(new Movie("interstellar", "Interstellar", "Sci-Fi", 169, "English", "PG-13", "A group of explorers travels beyond Earth in search of a future for humanity.", "/images/interstellar.jpg", 300));
        add(new Movie("inception", "Inception", "Sci-Fi", 148, "English", "PG-13", "A specialist in entering dreams is offered a chance to complete one final, unusual job.", "/images/inception.jpg", 300));
        add(new Movie("avengers-endgame", "Avengers: Endgame", "Action", 181, "English", "PG-13", "The surviving Avengers attempt to undo the devastation caused by Thanos.", "/images/avengers-endgame.jpg", 320));
        add(new Movie("dark-knight", "The Dark Knight", "Thriller", 152, "English", "PG-13", "Batman faces a chaotic new threat that pushes Gotham City and its heroes to their limits.", "/images/the-dark-knight.jpg", 280));
        add(new Movie("inside-out-2", "Inside Out 2", "Animation", 96, "English", "PG", "Riley enters her teenage years as new emotions arrive at headquarters.", "/images/inside-out-2.jpg", 240));
        add(new Movie("martian", "The Martian", "Sci-Fi", 144, "English", "PG-13", "An astronaut stranded on Mars uses science and ingenuity to stay alive and signal Earth.", "/images/the-martian.jpg", 280));
        add(new Movie("spider-man-no-way-home", "Spider-Man: No Way Home", "Action", 148, "English", "PG-13", "Peter Parker asks for help after his identity becomes public, with unexpected consequences.", "/images/spider-man-no-way-home.jpg", 300));
        add(new Movie("la-la-land", "La La Land", "Romance", 128, "English", "PG-13", "An aspiring actor and a jazz musician fall in love while pursuing their creative ambitions in Los Angeles.", "/images/la-la-land.jpg", 240));
        add(new Movie("parasite", "Parasite", "Thriller", 132, "Korean", "R", "A poor family gradually becomes entangled with a wealthy household, with unsettling results.", "/images/parasite.jpg", 260));
        add(new Movie("coco", "Coco", "Animation", 105, "English", "PG", "An aspiring young musician enters the Land of the Dead and uncovers a family secret.", "/images/coco.jpg", 240));
        // Optional profile metadata. Empty values are intentionally omitted by the view when unknown.
        profile("three-idiots", 2009, "Rajkumar Hirani", "Aamir Khan, R. Madhavan, Sharman Joshi", "4.5");
        profile("dangal", 2016, "Nitesh Tiwari", "Aamir Khan, Fatima Sana Shaikh, Sanya Malhotra", "4.5");
        profile("jawan", 2023, "Atlee", "Shah Rukh Khan, Nayanthara, Vijay Sethupathi", "4.0");
        profile("pathaan", 2023, "Siddharth Anand", "Shah Rukh Khan, Deepika Padukone, John Abraham", "4.0");
        profile("rrr", 2022, "S. S. Rajamouli", "N. T. Rama Rao Jr., Ram Charan, Alia Bhatt", "4.5");
        profile("baahubali", 2015, "S. S. Rajamouli", "Prabhas, Rana Daggubati, Anushka Shetty", "4.5");
        profile("interstellar", 2014, "Christopher Nolan", "Matthew McConaughey, Anne Hathaway, Jessica Chastain", "4.5");
        profile("inception", 2010, "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", "4.5");
        profile("avengers-endgame", 2019, "Anthony and Joe Russo", "Robert Downey Jr., Chris Evans, Mark Ruffalo", "4.5");
        profile("dark-knight", 2008, "Christopher Nolan", "Christian Bale, Heath Ledger, Aaron Eckhart", "4.5");
        profile("inside-out-2", 2024, "Kelsey Mann", "Amy Poehler, Maya Hawke, Kensington Tallman", "4.0");
        profile("martian", 2015, "Ridley Scott", "Matt Damon, Jessica Chastain, Chiwetel Ejiofor", "4.0");
        profile("spider-man-no-way-home", 2021, "Jon Watts", "Tom Holland, Zendaya, Benedict Cumberbatch", "4.0");
        profile("la-la-land", 2016, "Damien Chazelle", "Ryan Gosling, Emma Stone, John Legend", "4.0");
        profile("parasite", 2019, "Bong Joon Ho", "Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong", "4.5");
        profile("coco", 2017, "Lee Unkrich", "Anthony Gonzalez, Gael García Bernal, Benjamin Bratt", "4.5");
    }

    private void add(Movie movie) { movies.put(movie.getId(), movie); }
    private void profile(String id, int year, String director, String cast, String illustrativeRating) {
        Movie movie = movies.get(id);
        if (movie != null) {
            movie.setReleaseYear(year);
            movie.setDirector(director);
            movie.setCast(cast);
            movie.setIllustrativeRating(illustrativeRating);
        }
    }
    @Override public List<Movie> getAllMovies() { return new ArrayList<>(movies.values()); }
    @Override public Movie findById(String id) { return movies.get(id); }
}

