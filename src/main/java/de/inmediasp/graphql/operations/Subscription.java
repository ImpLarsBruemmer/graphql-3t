package de.inmediasp.graphql.operations;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import de.inmediasp.graphql.persistence.Flight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class Subscription implements GraphQLSubscriptionResolver {

    private final Flux<Flight> flightSource;


    public Publisher<Flight> flightChanged(String start, String destination) {
        Flux<Flight> flux = flightSource;

        if (start != null && !start.isBlank()) {
            flux = flux.filter(it -> start.equalsIgnoreCase(it.getStart()));
        }

        if (destination != null && !destination.isBlank()) {
            flux = flux.filter(it -> destination.equalsIgnoreCase(it.getDestination()));
        }

        return flux.map(it -> {
            log.info("event for flight change");
            return it;
        });
    }
}
