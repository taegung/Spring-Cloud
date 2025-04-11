package dev.petclinic.web;

import dev.petclinic.dto.ReservationRequest;
import dev.petclinic.model.Reservation;
import dev.petclinic.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/reservations")
@RequiredArgsConstructor
public class ReservationResource {

    private final ReservationService reservationService;

    /*
     모든 예약 내역 조화
     */
    @GetMapping
    public Flux<Reservation> getAllReservation(){
        return reservationService.getAllReservations();
    }

    //예약 접수
    @PostMapping
    public Mono<Reservation> submitReservation(@RequestBody ReservationRequest reservationRequest){
        System.out.println("reservationRequest = " + reservationRequest);//soutv

        return reservationService.submitReservation(reservationRequest);
    }


}
