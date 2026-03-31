package com.railway.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Train operating in the railway system.
 * Keeps track of available seats per SeatClass.
 */
public class Train {
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private double baseFare;
    private Map<SeatClass, Integer> availableSeats;

    public Train(String trainNumber, String trainName, String source, String destination, double baseFare) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.baseFare = baseFare;
        this.availableSeats = new HashMap<>();
    }

    public void setSeats(SeatClass seatClass, int seats) {
        availableSeats.put(seatClass, seats);
    }

    public int getAvailableSeats(SeatClass seatClass) {
        return availableSeats.getOrDefault(seatClass, 0);
    }

    public void reduceSeats(SeatClass seatClass, int count) {
        int current = getAvailableSeats(seatClass);
        availableSeats.put(seatClass, current - count);
    }

    public String getTrainNumber() { return trainNumber; }
    public String getTrainName() { return trainName; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getBaseFare() { return baseFare; }

    @Override
    public String toString() {
        return String.format("%s - %s | %s to %s | Base Fare: $%.2f", trainNumber, trainName, source, destination, baseFare);
    }
}
