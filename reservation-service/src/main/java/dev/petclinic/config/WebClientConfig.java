package dev.petclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean // WebClient를 빈으로 사용하기 위해 스프링 빈으로 등록
    WebClient webClient(WebClient.Builder webClientBuilder) {
        // WebClient.Builder - 스프링 부트의 Auto Configuration으로 설정된 빈,
        // WebClient 빈 생성 시 활용됨

        // TODO: baseUrl을 설정 파일로 분리
        return webClientBuilder.baseUrl("http://localhost:8081").build();

    }
}
