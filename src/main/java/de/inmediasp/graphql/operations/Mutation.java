package de.inmediasp.graphql.operations;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final FlightRepository flightRepository;
    
    private final Sinks.Many<Flight> flightSink;

    public Flight addFlight(final FlightInput flight) {
        final Flight newFlight = Flight
                .builder()
                .arrival(flight.getArrival())
                .departure(flight.getDeparture())
                .start(flight.getStart())
                .destination(flight.getDestination())
                .status(flight.getStatus())
                .build();

        final Flight savedFlight = flightRepository.save(newFlight);

        flightSink.tryEmitNext(savedFlight);

        return savedFlight;
    }

    public Flight changeFlight(final FlightInput flight) {
        return flightRepository
                .findById(flight.getId())
                .map(flightFromDb -> updateFlight(flightFromDb, flight))
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    private Flight updateFlight(final Flight flightFromDb, final FlightInput flight) {
        flightFromDb.setArrival(flight.getArrival());
        flightFromDb.setDeparture(flight.getDeparture());
        flightFromDb.setDestination(flight.getDestination());
        flightFromDb.setStart(flight.getStart());
        flightFromDb.setStatus(flight.getStatus());

        final Flight savedFlight = flightRepository.save(flightFromDb);

        flightSink.tryEmitNext(savedFlight);

        return savedFlight;
    }
}
