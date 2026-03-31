package com.railway.exceptions;

/**
 * General exception thrown when a booking fails.
 */
public class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}
