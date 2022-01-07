package de.inmediasp.graphql.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;
import de.inmediasp.graphql.persistence.FlightState;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {
    private final FlightRepository flightRepository;

    @Autowired
    public Query(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Board getCurrentBoard(final BoardType type, final String start, final String destination, final FlightState status) {
        //@formatter:off
        return Board
                .builder()
                .type(type)
                .title(type.toString())
                .start(start)
                .destination(destination)
                .status(status)
                .build();
        //@formatter:on
    }

    public Flight getFlight(final Long id) {
        return flightRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
