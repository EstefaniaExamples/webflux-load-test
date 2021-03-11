package com.example.listdemo;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class AppContext {

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client -> client
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(1000))
                                .addHandlerLast(new WriteTimeoutHandler(1000)))
                );

        final ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl("https://industrial-aggregator-pre.santanderuk.pre.corp")
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
