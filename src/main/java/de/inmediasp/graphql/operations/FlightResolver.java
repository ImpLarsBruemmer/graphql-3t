package de.inmediasp.graphql.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.Passenger;
import de.inmediasp.graphql.persistence.PassengerRepository;

@Component
public class FlightResolver implements GraphQLResolver<Flight> {
    private final PassengerRepository passengerRepository;

    @Autowired
    public FlightResolver(final PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getPassengers(final Flight flight) {
        return passengerRepository.findAllByFlight(flight);
    }
}