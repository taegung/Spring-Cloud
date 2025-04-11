package dev.petclinic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "reservations")
public record Reservation (
        @Id
        Long id,

        Long ownerId,
        Long petId,
        Long vetId,
        LocalDate reservationDate,
        ReservationStatus status
) {
    public static Reservation of(Long ownerId, Long petId, Long vetId, LocalDate reservationDate, ReservationStatus status) {
        return new Reservation(null, ownerId, petId, vetId, reservationDate, status);
    }
}
