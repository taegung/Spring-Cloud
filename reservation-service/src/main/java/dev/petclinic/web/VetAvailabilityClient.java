package dev.petclinic.web;

import dev.petclinic.dto.VetAvailability;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
    마이크로 서비스 간 통신 과정에서 활용되는 클래스인 WebClient를 감싼(Wrapping) 클래스
    수의사 서비스 전용 요청 클라이언트

    * 예약 서비스 → 수의사 서비스로 요청 전달 시 활용되는 API 클라이언트
 */
@Component // 스프링에서 활용할 수 있게 빈으로 등록
public class VetAvailabilityClient {

    private static final String VETS_ROOT_API = "/vets/";

    // 실제 요청 전송은 WebClient가 수행
    private final WebClient webClient;

    public VetAvailabilityClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // 해당 메서드를 통해 요청이 수행됨
    // 다른 클래스에서는 VetAvailabilityClient.getVetAvailabilityById()와 같은 형식으로 호출
    public Mono<VetAvailability> getVetAvailabilityById(Long id) {
        return webClient
                .get() // GET 요청
                .uri(VETS_ROOT_API + id) // 요청 URL은 vets/{vetId}
                .retrieve() // 요청을 보낸 후 응답을 받음
                .bodyToMono(VetAvailability.class); // 받은 객체를 Mono<VetAvailability>로 변환

        //Todo: 복원력(Resilience) 향상
            // 방법1. 커넥션 타임아웃(TimeOut) 지정, 원격 서비스에 요청을 응답을 받기까지 기다릴 수 있음
            // 방법2. 재시도(Retry) 쵯수 및 시간 지정
                    //ex. 재시도를 시스템이 응답할 떄까지 쉬지 않고 수행할 경우? 서버에 부하가 올 수 있음
                    //지수 백오프(exponential backoff) 전략 활용
                        //재시도 횟수가 늘어남에 따라 응답을 하기까지 지연되는 시간도 서서히 늘리는 것
                        //(서비스가 회복되고 응답할 수 있는 시간을 추가 위함)
                        // 방식 - 각 재시도 요청마다 지연 시간이 요청을 시도한 횟수의 100ms(초기 백오프값)을 곱한 값으로 계산되도록 정굑

    }
}
