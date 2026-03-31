package com.railway.models;

import java.time.LocalDate;

/**
 * Represents a confirmed booking ticket.
 */
public class Ticket {
    private String pnr;
    private Train train;
    private Passenger passenger;
    private SeatClass seatClass;
    private LocalDate journeyDate;
    private double totalFare;

    public Ticket(String pnr, Train train, Passenger passenger, SeatClass seatClass, LocalDate journeyDate, double totalFare) {
        this.pnr = pnr;
        this.train = train;
        this.passenger = passenger;
        this.seatClass = seatClass;
        this.journeyDate = journeyDate;
        this.totalFare = totalFare;
    }

    public String getPnr() { return pnr; }
    public Train getTrain() { return train; }
    public Passenger getPassenger() { return passenger; }
    public SeatClass getSeatClass() { return seatClass; }
    public LocalDate getJourneyDate() { return journeyDate; }
    public double getTotalFare() { return totalFare; }

    @Override
    public String toString() {
        return String.format(
            "----- TICKET -----\nPNR: %s\nTrain: %s (%s)\nPassenger: %s\nClass: %s\nDate: %s\nTotal Fare: $%.2f\n------------------",
            pnr, train.getTrainName(), train.getTrainNumber(),
            passenger.toString(), seatClass, journeyDate, totalFare
        );
    }
}
