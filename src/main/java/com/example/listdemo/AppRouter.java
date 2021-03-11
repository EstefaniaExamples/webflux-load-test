package com.example.listdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AppRouter {
    private final AppHandler appHandler;

    public AppRouter(final AppHandler appHandler) {
        this.appHandler = appHandler;
    }

    @Bean
    RouterFunction<ServerResponse> home() {
        return route(GET("/accounts"), appHandler::getAccountWithBalances);
    }
}
