package com.railway.exceptions;

/**
 * Thrown when attempting to book a ticket but no seats are available.
 */
public class TrainCapacityException extends BookingException {
    public TrainCapacityException(String message) {
        super(message);
    }
}
