package com.SpringWebclient.Task7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/data")
public class PostController {
    WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @PostMapping("/addData")
    public Mono<Post> addData(@RequestBody Post post){
        return webClient.post()
                .uri("/posts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(post))
                .retrieve()
                .bodyToMono(Post.class);
    }

    @GetMapping("/getall")
    public Flux<Post> getAll() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class);
    }

    @GetMapping("/getbyid/{Id}")
    public Post getPostById(@PathVariable int Id) {

        return webClient
                .get()
                .uri("/posts/{Id}", Id)
                .retrieve()
                .bodyToMono(Post.class)
                .block();
    }

    @DeleteMapping("/remove/{Id}")
    public Mono<Post> RemovePost(@PathVariable int Id) {
        return webClient
                .delete()
                .uri("/posts/" + Id)
                .retrieve()
                .bodyToMono(Post.class);
    }

    @PutMapping("/updatepost/{Id}")
    public Mono<Post> updatePost(@PathVariable int Id,@RequestBody Post request) {
        return webClient.put()
                .uri("/posts/" + Id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(Post.class);
    }
}
