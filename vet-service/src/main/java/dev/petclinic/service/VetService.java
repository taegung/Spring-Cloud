package dev.petclinic.service;


import dev.petclinic.model.Vet;
import dev.petclinic.model.VetAvailability;
import dev.petclinic.model.VetAvailabilityRepository;
import dev.petclinic.model.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetAvailabilityRepository vetAvailabilityRepository;
    private final VetRepository vetRepository;

    public List<Vet> getVetList() {
        return vetRepository.findAll();
    }

    public VetAvailability getVetById(@PathVariable Long vetId) {
        return vetAvailabilityRepository.findByVetId(vetId);
    }

}
