package com.SpringWebclient.Task7.task5;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/auth")
public class NextController {

    WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @PostMapping("/get")
    private Mono<String> getproducts(@RequestBody User user){
        Mono<jwtResponse> jwt = webClient.post()
               .uri("/authenticate")
               .body(BodyInserters.fromValue(user))
               .retrieve()
               .bodyToMono(jwtResponse.class);

        Mono<String> rslt = webClient.get()
                .uri("/products")
                .header("Authorization", "Bearer " + jwt.block().getAccessToken())
                .retrieve()
                .bodyToMono(String.class);
        return rslt;
    }

}
