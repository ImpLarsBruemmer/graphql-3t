package de.inmediasp.graphql.operations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.inmediasp.graphql.persistence.Flight;
import de.inmediasp.graphql.persistence.FlightRepository;
import de.inmediasp.graphql.security.User;
import de.inmediasp.graphql.security.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Sinks;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final UserService userService;
    private final FlightRepository flightRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;


    private final Sinks.Many<Flight> flightSink;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Flight addFlight(final FlightInput flight) {
        final Flight newFlight = Flight
                .builder()
                .arrival(flight.getArrival())
                .departure(flight.getDeparture())
                .start(flight.getStart())
                .destination(flight.getDestination())
                .status(flight.getStatus())
                .passengers(Collections.emptyList())
                .build();

        final Flight savedFlight = flightRepository.save(newFlight);

        flightSink.tryEmitNext(savedFlight);


        return savedFlight;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
        flightFromDb.setPassengers(Collections.emptyList());

        final Flight savedFlight = flightRepository.save(flightFromDb);

        flightSink.tryEmitNext(savedFlight);

        return savedFlight;
    }

    @PreAuthorize("isAnonymous()")
    public User login(final String email, final String password) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return userService.getCurrentUser();
        } catch (final AuthenticationException e) {
            throw new BadCredentialsException(email);
        }
    }
}
