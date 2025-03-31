package com.coworking.persistence;

import com.coworking.models.CoworkingSpace;
import java.util.List;

public class CoworkingSpaceStorage extends FileDataStorage<CoworkingSpace> {
    public CoworkingSpaceStorage(String filename) {
        super(filename);
    }
}