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
    public List<Flight> getFlights(final Board board, final List<FlightState> states) {
        return states == null
                ? flightRepository.findAll()
                : flightRepository.findAllByStatusIn(states);
    }
}
