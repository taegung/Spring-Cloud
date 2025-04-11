package dev.petclinic.event;


import dev.petclinic.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/*
   스케줄링 서비스에 의해 예약이 확정되면,
   스케줄링 서비스 애플리케이션이 생성한 메시지를 이벤트 브로커에서 꺼내 소비하는 함수 구현

   이 함수는 메시지를 수신하고, DB 내 Reservations 테이블에서 예약 상태를 업데이트해야함

   이 맥락에서 예약 서비스는 Consumer가 됨
 */
@Configuration
@Slf4j
public class ReservationFunctions {

    @Bean
    public Consumer<Flux<ReservationDispatchedMessage>> dispatchReservation(ReservationService reservationService) {

        // DB 내 Reservations 테이블의 예약 상태 업데이트
        return flux ->
                reservationService.consumeReservationDispatchedEvent(flux) // 예약 확정 이벤트 처리
                        .doOnNext(reservation
                                -> log.info(" == 스케줄링 처리 완료 이벤트가 전송됨, id: {}", reservation.id()))
                        .subscribe(); // 리액티브 스트림을 활성화하기 위해서는 구독, 구독자가 없으면 스트림을 통해 데이터가 흐르지 않음
    }
}
