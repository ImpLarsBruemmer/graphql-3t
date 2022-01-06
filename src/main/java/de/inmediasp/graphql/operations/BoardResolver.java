package de.inmediasp.graphql.operations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

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
public class BoardResolver implements GraphQLResolver<Board> {
    private final FlightRepository flightRepository;

    @Autowired
    public BoardResolver(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional
    public List<Flight> getFlights(final Board board) {
        final List<Flight> flights = board.getType() == BoardType.ARRIVALS ? getArrivingFlights() : getDepartingFlights();

        return filterFlights(flights, board.getStatus(), board.getStart(), board.getDestination());
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
                .collect(Collectors.toList());
        //@formatter:on
    }

    private boolean isDestination(final String destination, final Flight flight) {
        return destination == null || flight.getDestination().equals(destination);
    }

    private boolean isStart(final String start, final Flight flight) {
        return start == null || flight.getStart().equals(start);
    }

    private boolean isFlightState(final FlightState status, final Flight flight) {
        return status == null || flight.getStatus().equals(status);
    }
}
