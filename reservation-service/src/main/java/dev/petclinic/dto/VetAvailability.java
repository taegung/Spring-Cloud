package dev.petclinic.dto;

import java.util.List;

public record VetAvailability(
        Long id,
        Vet vet,
        List<String> availableDays
) {
}
