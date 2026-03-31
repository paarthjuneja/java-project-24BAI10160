package com.railway.models;

/**
 * Enum representing the different classes of seating available on a train.
 * Includes a base fare multiplier.
 */
public enum SeatClass {
    SLEEPER(1.0),
    AC(2.5),
    GENERAL(0.5);

    private final double fareMultiplier;

    SeatClass(double fareMultiplier) {
        this.fareMultiplier = fareMultiplier;
    }

    public double getFareMultiplier() {
        return fareMultiplier;
    }
}
