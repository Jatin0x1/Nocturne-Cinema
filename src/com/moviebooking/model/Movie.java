package com.moviebooking.model;

public class Movie extends CatalogItem {
    private String genre;
    private int durationMinutes;
    private String language;
    private String ageRating;
    private String description;
    private String posterUrl;
    private int ticketPrice;
    private int releaseYear;
    private String director;
    private String cast;
    private String illustrativeRating;

    public Movie(String id, String title, String genre, int durationMinutes, String language,
                 String ageRating, String description, String posterUrl, int ticketPrice) {
        super(id, title);
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.language = language;
        this.ageRating = ageRating;
        this.description = description;
        this.posterUrl = posterUrl;
        this.ticketPrice = ticketPrice;
    }

    @Override public String getDisplayLabel() { return getTitle() + " · " + genre; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getAgeRating() { return ageRating; }
    public void setAgeRating(String ageRating) { this.ageRating = ageRating; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public int getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(int ticketPrice) { this.ticketPrice = ticketPrice; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }
    public String getIllustrativeRating() { return illustrativeRating; }
    public void setIllustrativeRating(String illustrativeRating) { this.illustrativeRating = illustrativeRating; }
}
