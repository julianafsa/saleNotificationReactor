package org.example.notifications;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class CartNotification {

    private final Sinks.Many<Integer> cartNotifications = Sinks.many().multicast().onBackpressureBuffer();

    public void send(Integer productId) {
        cartNotifications.tryEmitNext(productId);
    }

    public Flux<Integer> getCartFlux() {
        return cartNotifications.asFlux();
    }

}
