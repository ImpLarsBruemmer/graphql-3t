package de.inmediasp.graphql.operations;

import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.inmediasp.graphql.persistence.Flight;

@Component
public class BoardResolver implements GraphQLResolver<Board> {
    public List<Flight> getFlights(final Board board) {
        return board.getFlights();
    }
}
