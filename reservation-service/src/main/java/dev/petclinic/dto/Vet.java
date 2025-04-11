package dev.petclinic.dto;

import java.util.List;

public record Vet(
        Long id,
        String firstName,
        String lastName,
        List<Specialty> specialties // Specialty는 별도의 레코드 타입으로 정의된다고 가정
) {
}

