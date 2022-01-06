package de.inmediasp.graphql.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;

@Component
public class Mutation implements GraphQLMutationResolver {
    private final FlightRepository flightRepository;

    @Autowired
    public Mutation(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(final FlightInput flight) {
        final Flight newFlight = Flight
                .builder()
                .arrival(flight.getArrival())
                .departure(flight.getDeparture())
                .start(flight.getStart())
                .destination(flight.getDestination())
                .status(flight.getStatus())
                .build();

        return flightRepository.save(newFlight);
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

        return flightRepository.save(flightFromDb);
    }
}
