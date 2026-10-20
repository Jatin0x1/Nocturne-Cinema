package com.moviebooking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reference;
    private String movieTitle;
    private String showDate;
    private String showTime;
    private List<String> seats;
    private int totalAmount;

    public Booking(String reference, String movieTitle, String showDate, String showTime, List<String> seats, int totalAmount) {
        this.reference = reference;
        this.movieTitle = movieTitle;
        this.showDate = showDate;
        this.showTime = showTime;
        this.seats = new ArrayList<>(seats);
        this.totalAmount = totalAmount;
    }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getShowDate() { return showDate; }
    public void setShowDate(String showDate) { this.showDate = showDate; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
    public List<String> getSeats() { return new ArrayList<>(seats); }
    public void setSeats(List<String> seats) { this.seats = new ArrayList<>(seats); }
    public int getTicketCount() { return seats.size(); }
    public int getTotalAmount() { return totalAmount; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }
}
