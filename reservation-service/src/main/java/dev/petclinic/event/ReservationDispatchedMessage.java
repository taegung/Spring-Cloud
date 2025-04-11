package dev.petclinic.event;

// 스케줄 서비스가 스케줄 처리 완료된 큐로 발행한 메시지 객체 포맷
// -> 예약 서비스가 처리할 이벤트 객체, 예약된 예약 내역에 대한 ID(식별자)를 가지고 있음
public record ReservationDispatchedMessage(
        Long reservationId // 스케줄 서비스에서 발행한 필드명이 같아야함
) {}
