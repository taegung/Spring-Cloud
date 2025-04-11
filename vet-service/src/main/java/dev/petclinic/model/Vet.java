package dev.petclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "vets")
@Getter
@ToString
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Specialty> specialties;

}