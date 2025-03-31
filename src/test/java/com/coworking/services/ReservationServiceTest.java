package com.coworking.services;

import com.coworking.dto.ReservationRequest;
import com.coworking.models.CoworkingSpace;
import com.coworking.models.Reservation;
import com.coworking.exceptions.ReservationException;
import com.coworking.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void makeReservation_WhenNoConflict_CreatesReservation() throws ReservationException {
        // Arrange
        CoworkingSpace space = new CoworkingSpace(1L, "Private", 50.0);
        ReservationRequest request = new ReservationRequest(
                space, "John", LocalDate.now(),
                LocalTime.of(9, 0), LocalTime.of(12, 0)
        );

        when(reservationRepository.getNextReservationId()).thenReturn(Optional.of(1L));
        when(reservationRepository.hasConflictingReservation(any())).thenReturn(false);

        // Act
        Optional<Reservation> result = reservationService.makeReservation(request);

        // Assert
        assertTrue(result.isPresent());
        verify(reservationRepository).addReservation(any());
    }

    @Test
    void makeReservation_WhenTimeConflict_ThrowsException() {
        // Arrange
        CoworkingSpace space = new CoworkingSpace(1L, "Private", 50.0);
        ReservationRequest request = new ReservationRequest(
                space, "John", LocalDate.now(),
                LocalTime.of(9, 0), LocalTime.of(12, 0)
        );

        when(reservationRepository.getNextReservationId()).thenReturn(Optional.of(1L));
        when(reservationRepository.hasConflictingReservation(any())).thenReturn(true);

        // Act & Assert
        assertThrows(ReservationException.class, () -> {
            reservationService.makeReservation(request);
        });
    }
}