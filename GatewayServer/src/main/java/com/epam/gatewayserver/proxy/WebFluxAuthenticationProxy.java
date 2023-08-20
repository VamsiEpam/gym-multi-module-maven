package com.epam.gatewayserver.proxy;


import com.epam.gatewayserver.dto.AuthRequest;
import com.epam.gatewayserver.helper.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class WebFluxAuthenticationProxy {


    private WebClient.Builder webClientBuilder;
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @Autowired
    public WebFluxAuthenticationProxy(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.webClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }
//    public Mono<String> getToken(AuthRequest authRequest) {
//        return webClientBuilder
//                .build()
//                .post() // Use POST method
//                .uri("http://gym-authentication-service/auth/login")
//                .contentType(MediaType.APPLICATION_JSON) // Set content type
//                .bodyValue(authRequest) // Add your request body object
//                .retrieve()
//                .bodyToMono(String.class)
//                .subscribeOn(Schedulers.boundedElastic());
//    }


    public Mono<String> validateToken(String token) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"validateToken",token);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"validateToken");
        return webClientBuilder.build().get()
                .uri("http://gym-authentication-service/auth/validate?token=" + token)
                .retrieve()
                .bodyToMono(String.class)
                .subscribeOn(Schedulers.boundedElastic());

    }
}
