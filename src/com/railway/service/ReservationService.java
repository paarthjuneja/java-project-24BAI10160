package com.railway.service;

import com.railway.exceptions.BookingException;
import com.railway.exceptions.TrainCapacityException;
import com.railway.models.Passenger;
import com.railway.models.SeatClass;
import com.railway.models.Ticket;
import com.railway.models.Train;
import com.railway.repository.FileStorage;
import com.railway.repository.TrainRepository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Business logic service for handling reservations.
 */
public class ReservationService {
    private final TrainRepository trainRepository;

    public ReservationService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    /**
     * Attempts to book a ticket for the specified train and class.
     * Throws an exception if validation fails or seats are full.
     */
    public Ticket bookTicket(String trainNumber, Passenger passenger, SeatClass seatClass, LocalDate journeyDate) throws BookingException {
        Train train = trainRepository.findTrainByNumber(trainNumber)
                .orElseThrow(() -> new BookingException("Train not found with number: " + trainNumber));

        int availableSeats = train.getAvailableSeats(seatClass);
        if (availableSeats <= 0) {
            throw new TrainCapacityException("No seats available in " + seatClass + " class for train " + trainNumber);
        }

        // Calculate Fare based on base fare and class multiplier
        double totalFare = train.getBaseFare() * seatClass.getFareMultiplier();

        // Generate a random unique PNR
        String pnr = "PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Ticket ticket = new Ticket(pnr, train, passenger, seatClass, journeyDate, totalFare);

        // Deduct seat and update the persistent state
        train.reduceSeats(seatClass, 1);
        trainRepository.updateTrains();

        // Save booking record to the file system
        String ticketRecord = String.format("%s,%s,%s,%s,%s,%s,%.2f",
                ticket.getPnr(), train.getTrainNumber(), passenger.getName(), passenger.getAge(),
                seatClass.name(), journeyDate.toString(), totalFare);
        FileStorage.saveBooking(ticketRecord);

        return ticket;
    }
}
