package com.coworking.services;

import com.coworking.models.CoworkingSpace;
import com.coworking.repositories.ReservationRepository;
import com.coworking.repositories.SpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceServiceTest {

    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private SpaceService spaceService;

    @BeforeEach
    void setUp() {
        spaceService = new SpaceService(spaceRepository, reservationRepository);
    }

    @Test
    void getAvailableSpaces_WhenNoReservations_ReturnsAllSpaces() {
        // Arrange
        CoworkingSpace space1 = new CoworkingSpace(1L, "Private", 50.0);
        CoworkingSpace space2 = new CoworkingSpace(2L, "Open", 20.0);

        when(spaceRepository.getAllSpaces()).thenReturn(List.of(space1, space2));
        when(reservationRepository.getAllReservations()).thenReturn(Collections.emptyList());

        // Act
        List<CoworkingSpace> availableSpaces = spaceService.getAvailableSpaces();

        // Assert
        assertEquals(2, availableSpaces.size());
        assertTrue(availableSpaces.contains(space1));
        assertTrue(availableSpaces.contains(space2));
    }

    @Test
    void getSpaceById_WhenSpaceExists_ReturnsSpace() {
        // Arrange
        CoworkingSpace space = new CoworkingSpace(1L, "Private", 50.0);
        when(spaceRepository.getSpaceById(1L)).thenReturn(Optional.of(space));

        // Act
        Optional<CoworkingSpace> result = spaceService.getSpaceById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(space, result.get());
    }
}