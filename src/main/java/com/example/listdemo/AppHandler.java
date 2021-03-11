package com.example.listdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class AppHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppHandler.class);
    private final WebClient webClient;

    public AppHandler(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ServerResponse> getAccountWithBalances(final ServerRequest request) {
        final List<String> accounts = List.of("account-1", "account-2", "account-3", "account-4");

        return Flux.fromIterable(accounts)
                .log(Thread.currentThread().getName())
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(account ->
                        webClient.get().uri("/stubs/accounts")
                                .retrieve()
                                .bodyToMono(Object.class)
                )
                .doOnError(throwable -> LOGGER.error("Error during runtime ", throwable))
                .sequential()
                .collectList()
        .flatMap(accountsWithBalances ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(accountsWithBalances));

    }
}
