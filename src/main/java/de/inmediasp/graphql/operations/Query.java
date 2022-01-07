package de.inmediasp.graphql.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;
import de.inmediasp.graphql.persistence.FlightState;

@Component
public class Query implements GraphQLQueryResolver {
    private final FlightRepository flightRepository;

    @Autowired
    public Query(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
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

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Flight getFlight(final Long id) {
        return flightRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
