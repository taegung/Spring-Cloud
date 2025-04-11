package dev.petclinic.web;

import dev.petclinic.model.Vet;
import dev.petclinic.model.VetAvailability;
import dev.petclinic.service.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 본질적으로는 Controller 역할 수행
 */

@RequestMapping("/vets")
@RestController
@RequiredArgsConstructor
public class VetResource {

    private final VetService vetService;

    // 전체 수의사 정보 조회
    @GetMapping
    public List<Vet> getVetList() {
        return vetService.getVetList();
    }

    // 수의사 ID로 해당 수의사의 진료 가능한 요일 조회
    @GetMapping("/{vetId}")
    public VetAvailability getVetById(@PathVariable Long vetId) throws InterruptedException {

        return vetService.getVetById(vetId);
    }
}
