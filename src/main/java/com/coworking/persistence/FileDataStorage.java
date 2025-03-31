package com.coworking.persistence;

import com.coworking.exceptions.StorageException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileDataStorage<T> implements DataStorage<T> {
    protected final String filename;

    public FileDataStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(List<T> items) throws StorageException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(items);
        } catch (IOException e) {
            throw new StorageException("Failed to save data to " + filename, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> load() throws StorageException {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Failed to load data from " + filename, e);
        }
    }
}