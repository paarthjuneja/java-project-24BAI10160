package com.railway.repository;

import com.railway.models.Train;

import java.util.List;
import java.util.Optional;

/**
 * Manages the collection of trains.
 * Acts as an in-memory database synchronized with FileStorage.
 */
public class TrainRepository {
    private List<Train> trains;

    public TrainRepository() {
        this.trains = FileStorage.loadTrains();
    }

    public List<Train> getAllTrains() {
        return trains;
    }

    public Optional<Train> findTrainByNumber(String trainNumber) {
        return trains.stream()
                .filter(t -> t.getTrainNumber().equals(trainNumber))
                .findFirst();
    }

    public void updateTrains() {
        FileStorage.saveTrains(trains);
    }
}
