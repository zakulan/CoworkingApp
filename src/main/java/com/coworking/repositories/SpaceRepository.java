package com.coworking.repositories;

import com.coworking.models.CoworkingSpace;
import com.coworking.persistence.DataStorage;
import com.coworking.exceptions.StorageException;
import java.util.*;
import java.util.stream.Collectors;

public class SpaceRepository {
    private final DataStorage<CoworkingSpace> storage;
    private List<CoworkingSpace> spaces;
    private Long spaceIdCounter = 1L;

    public SpaceRepository(DataStorage<CoworkingSpace> storage) {
        this.storage = storage;
        this.spaces = loadSpaces();
        initializeIdCounter();
    }

    private List<CoworkingSpace> loadSpaces() {
        try {
            return storage.load();
        } catch (StorageException e) {
            System.err.println("Error loading spaces: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void initializeIdCounter() {
        spaceIdCounter = spaces.stream()
                .mapToLong(CoworkingSpace::getId)
                .max()
                .orElse(0L) + 1L;
    }

    public void saveSpaces() {
        try {
            storage.save(spaces);
        } catch (StorageException e) {
            System.err.println("Error saving spaces: " + e.getMessage());
        }
    }

    public void addSpace(CoworkingSpace space) {
        space.setId(spaceIdCounter++);
        spaces.add(space);
        saveSpaces();
    }

    public List<CoworkingSpace> getAllSpaces() {
        return Collections.unmodifiableList(spaces);
    }

    public Optional<CoworkingSpace> getSpaceById(Long id) {
        return spaces.stream()
                .filter(space -> space.getId().equals(id))
                .findFirst();
    }

    public void removeSpace(Long id) {
        spaces.removeIf(space -> space.getId().equals(id));
        saveSpaces();
    }

    public List<CoworkingSpace> findSpacesByType(String type) {
        return spaces.stream()
                .filter(space -> space.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public Map<String, List<CoworkingSpace>> groupSpacesByType() {
        return spaces.stream()
                .collect(Collectors.groupingBy(
                        CoworkingSpace::getType,
                        Collectors.toList()
                ));
    }
}