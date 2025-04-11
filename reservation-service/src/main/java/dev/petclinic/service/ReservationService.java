package dev.petclinic.service;

import dev.petclinic.dto.ReservationRequest;
import dev.petclinic.dto.VetAvailability;
import dev.petclinic.event.ReservationAcceptedMessage;
import dev.petclinic.event.ReservationDispatchedMessage;
import dev.petclinic.model.Reservation;
import dev.petclinic.model.ReservationRepository;
import dev.petclinic.model.ReservationStatus;
import dev.petclinic.web.VetAvailabilityClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    // ReservationService에서 사용할 수 있도록 필드 추가
    private final VetAvailabilityClient vetClient;
    private final ReservationRepository reservationRepository;

    // ====== 메시지 큐 기능 추가 시 작성한 부분(시작) ======
    private final StreamBridge streamBridge;
    // ====== 메시지 큐 기능 추가 시 작성한 부분(끝) ======

    public Flux<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Mono<Reservation> submitReservation(ReservationRequest reservationRequest) {

        // 1. (예약 서비스에서) 수의사 서비스로 수의사 Id(vetId)를 통해 특정 수의사의 진료 일정 조회
        Mono<VetAvailability> vetAvailability
                = vetClient.getVetAvailabilityById(reservationRequest.vetId());

        // 2. 응답받은 수의사의 진료 일정 정보를 토대로 예약 접수 내역을 DB에 저장
        Mono<Reservation> reservationMono = vetAvailability
                .map(vet -> buildAcceptReservation(vet, reservationRequest)) // 예약 접수 수락
                .flatMap(reservationRepository::save)// 예약 서비스 DB에 예약 정보 저장
                // 예약이 접수되었다는 것 까지만 처리니까, 예약 상태(ReservationStatus)는 PENDING 상태로 저장됨

                // 2-1. 메시지 큐에 예약이 접수되었다는 이벤트를 적재
                // publishReservationAcceptedEvent - "메시지 큐에 예약이 접수되었다는 이벤트를 적재해라"
                .doOnNext(reservation -> publishReservationAcceptedEvent(reservation))
                .doOnSubscribe(subscription -> System.out.println("예약 프로세스에 대한 구독이 시작됨"));


        return reservationMono;
    }

    // PENDING 상태로 저장할 Reservation 객체 생성
    private static Reservation buildAcceptReservation(VetAvailability vet, ReservationRequest reservationRequest) {
        return Reservation.of(
                reservationRequest.ownerId(),
                reservationRequest.petId(),
                reservationRequest.vetId(),
                reservationRequest.reservationDate(),
                ReservationStatus.PENDING);
    }


    // 퍼블리셔(예약 서비스) 로직 구현 - 예약이 접수되면 브로커(RabbitMQ)에 예약 접수 메시지 전송
    private void publishReservationAcceptedEvent(Reservation reservation) {
        log.info(" == Reservation Accepted(Pending) 이벤트가 호출됨, id: {}", reservation.id());

        // 예약이 접수되었다는 이벤트 객체 생성
        var reservationAcceptedMessage = new ReservationAcceptedMessage(reservation.id());
        log.info(" == 예약 접수 이벤트가 전송됨, id: {}", reservation.id());

        // 큐에 적재
        boolean result // 메시지를 acceptReservation-out-0에 명시적으로 전송
                = streamBridge.send("acceptReservation-out-0", reservationAcceptedMessage);
        log.info(" == 적재 결과, {}", result);

    }

    public Flux<Reservation> consumeReservationDispatchedEvent(Flux<ReservationDispatchedMessage> flux) {
        return flux.flatMap(message ->
                        reservationRepository.findById(message.reservationId())) // id에 해당하는 예약 조회
                .map(this::buildDispatchedReservation) // 예약 상태를 Confired로 변경
                .flatMap(reservationRepository::save); // 저장
    }

    private Reservation buildDispatchedReservation(Reservation existingReservation) {
        return new Reservation(
                existingReservation.id(),
                existingReservation.ownerId(),
                existingReservation.petId(),
                existingReservation.vetId(),
                existingReservation.reservationDate(),
                ReservationStatus.CONFIRMED
        );
    }
}
