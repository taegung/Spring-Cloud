package dev.petclinic.dto;

import java.time.LocalDate;

public record ReservationRequest (
        Long ownerId,
        Long petId,
        Long vetId,
        LocalDate reservationDate
) {}
