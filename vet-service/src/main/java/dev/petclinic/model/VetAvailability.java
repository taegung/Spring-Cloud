package dev.petclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Getter
public class VetAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

    private byte availableDays;

    // 요일별 예약 가능 여부를 비트마스크로 설정
    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        byte mask = 0;
        for (DayOfWeek day : availableDays) {
            mask |= (1 << day.getValue() % 7); // DayOfWeek의 값은 1부터 시작하므로 7로 모듈러 연산
        }
        this.availableDays = mask;
    }

    // 특정 요일에 예약 가능한지 확인하는 메서드
    public boolean isAvailableOn(DayOfWeek day) {
        return (availableDays & (1 << day.getValue() % 7)) != 0;
    }

    // 예약 가능한 요일 목록을 반환하는 메서드
    public Set<DayOfWeek> getAvailableDays() {
        Set<DayOfWeek> days = new HashSet<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            if ((availableDays & (1 << day.getValue() % 7)) != 0) {
                days.add(day);
            }
        }
        return days;
    }

}

