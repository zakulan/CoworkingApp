package com.coworking.persistence;

import com.coworking.models.Reservation;
import java.util.List;

public class ReservationStorage extends FileDataStorage<Reservation> {
    public ReservationStorage(String filename) {
        super(filename);
    }
}