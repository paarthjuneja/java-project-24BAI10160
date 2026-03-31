package com.railway.repository;

import com.railway.models.SeatClass;
import com.railway.models.Train;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all IO operations and maintains persistent state.
 */
public class FileStorage {
    private static final String TRAIN_FILE = "trains.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    public static List<Train> loadTrains() {
        List<Train> trains = new ArrayList<>();
        Path path = Paths.get(TRAIN_FILE);

        if (!Files.exists(path)) {
            return trains;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    Train train = new Train(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                    train.setSeats(SeatClass.SLEEPER, Integer.parseInt(parts[5]));
                    train.setSeats(SeatClass.AC, Integer.parseInt(parts[6]));
                    train.setSeats(SeatClass.GENERAL, Integer.parseInt(parts[7]));
                    trains.add(train);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading trains file: " + e.getMessage());
        }
        return trains;
    }

    public static void saveTrains(List<Train> trains) {
        Path path = Paths.get(TRAIN_FILE);
        List<String> lines = new ArrayList<>();

        for (Train train : trains) {
            String line = String.format("%s,%s,%s,%s,%.2f,%d,%d,%d",
                    train.getTrainNumber(), train.getTrainName(), train.getSource(), train.getDestination(),
                    train.getBaseFare(),
                    train.getAvailableSeats(SeatClass.SLEEPER),
                    train.getAvailableSeats(SeatClass.AC),
                    train.getAvailableSeats(SeatClass.GENERAL));
            lines.add(line);
        }

        try {
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving trains: " + e.getMessage());
        }
    }

    public static void saveBooking(String ticketData) {
        Path path = Paths.get(BOOKINGS_FILE);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, (ticketData + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error saving booking: " + e.getMessage());
        }
    }
}
