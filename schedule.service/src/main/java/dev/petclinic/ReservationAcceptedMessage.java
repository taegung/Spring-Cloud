package dev.petclinic;

//예약 접수 큐에서 꺼낸 이벤트 객체의 타입과 일치하도록 작성된 record
public record ReservationAcceptedMessage(
        Long reservationId
) {}
