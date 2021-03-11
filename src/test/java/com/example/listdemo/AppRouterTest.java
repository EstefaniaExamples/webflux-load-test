package com.example.listdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppRouterTest {
    @Autowired
    private WebTestClient client;

    @Test
    public void test_home_page() {
        client.get().uri("/accounts").exchange().expectStatus().isOk();
    }

}
