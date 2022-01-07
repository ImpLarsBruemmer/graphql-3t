package de.inmediasp.graphql.operations;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;

import de.inmediasp.graphql.persistence.FlightState;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import de.inmediasp.graphql.persistence.Flight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Subscription implements GraphQLSubscriptionResolver {

    private final Flux<Flight> flightSource;

    public Publisher<Flight> flightChanged(final String start, final String destination, final Collection<FlightState> states) {
        Flux<Flight> flux = flightSource;

        if (start != null && !start.isBlank()) {
            flux = flux.filter(it -> start.equalsIgnoreCase(it.getStart()));
        }

        if (destination != null && !destination.isBlank()) {
            flux = flux.filter(it -> destination.equalsIgnoreCase(it.getDestination()));
        }

        if (states != null && !states.isEmpty()) {
            flux = flux.filter(it -> states.contains(it.getStatus()));
        }

        return flux.map(it -> {
            log.info("event for flight change");
            return it;
        });
    }
}
