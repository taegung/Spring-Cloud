package dev.petclinic.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface VetAvailabilityRepository extends JpaRepository<VetAvailability, Long> {

    VetAvailability findByVetId(@Param("id") Long vetId);

    // 예약 가능한 특정 요일을 조회하는 메서드
    @Query(value = "SELECT * FROM vet_availability WHERE (available_days & :dayMask) != 0", nativeQuery = true)
    List<VetAvailability> findAvailableVetsOnDay(@Param("dayMask") byte dayMask);

}
