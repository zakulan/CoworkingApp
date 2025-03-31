package com.coworking.persistence;

import com.coworking.exceptions.StorageException;
import java.util.List;

public interface DataStorage<T> {
    void save(List<T> items) throws StorageException;
    List<T> load() throws StorageException;
}