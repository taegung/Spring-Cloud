package dev.petclinic.event;

//메시지 큐에 적재됨 이벤트 객체, 예약 접수되었다는 정보를 가진 메시지
public record ReservationAcceptedMessage(
        Long reservationId //예약 ID
) {}