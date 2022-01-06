package de.inmediasp.graphql.operations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;
import de.inmediasp.graphql.persistence.FlightState;

import static de.inmediasp.graphql.persistence.FlightState.ARRIVING;
import static de.inmediasp.graphql.persistence.FlightState.BOARDING;
import static de.inmediasp.graphql.persistence.FlightState.CANCELLED;
import static de.inmediasp.graphql.persistence.FlightState.DELAYED;
import static de.inmediasp.graphql.persistence.FlightState.IN_TIME;
import static de.inmediasp.graphql.persistence.FlightState.LANDED;
import static de.inmediasp.graphql.persistence.FlightState.READY_FOR_TAKE_OFF;

@Component
public class Query implements GraphQLQueryResolver {
    private final FlightRepository flightRepository;

    @Autowired
    public Query(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Board getCurrentBoard(final BoardType type, final String start, final String destination, final FlightState status) {

        final List<Flight> flights = type == BoardType.ARRIVALS ? getArrivingFlights() : getDepartingFlights();

        //@formatter:off
        return Board
                .builder()
                .type(type)
                .title(type.toString())
                .flights(filterFlights(flights, status, start, destination))
                .build();
        //@formatter:on
    }

    private List<Flight> getArrivingFlights() {
        return flightRepository.findAllByStatusIn(Arrays.asList(LANDED, DELAYED, IN_TIME, ARRIVING));
    }

    private List<Flight> getDepartingFlights() {
        return flightRepository.findAllByStatusIn(Arrays.asList(READY_FOR_TAKE_OFF, BOARDING, CANCELLED));
    }

    private List<Flight> filterFlights(final List<Flight> flights, final FlightState status, final String start, final String destination) {
        //@formatter:off
        return flights
                .stream()
                .filter(flight -> isFlightState(status, flight))
                .filter(flight -> isStart(start, flight))
                .filter(flight -> isDestination(destination, flight))
                .toList();
        //@formatter:on
    }

    private boolean isDestination(String destination, Flight flight) {
        return destination == null || flight.getDestination().equals(destination);
    }

    private boolean isStart(String start, Flight flight) {
        return start == null || flight.getStart().equals(start);
    }

    private boolean isFlightState(FlightState status, Flight flight) {
        return status == null || flight.getStatus().equals(status);
    }
}
