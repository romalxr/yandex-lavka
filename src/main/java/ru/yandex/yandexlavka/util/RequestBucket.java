package ru.yandex.yandexlavka.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Component
public class RequestBucket {

    private static final int CAPACITY = 10;
    private final Bucket bucket;

    public RequestBucket() {
        Bandwidth limit = Bandwidth.classic(CAPACITY, Refill.greedy(CAPACITY, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public void consume() {
        if (!bucket.tryConsume(1)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests!");
        }
    }
}
