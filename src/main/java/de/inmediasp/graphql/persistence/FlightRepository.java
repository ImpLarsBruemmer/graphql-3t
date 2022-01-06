package de.inmediasp.graphql.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByStart(String start);

    List<Flight> findAllByDestination(String destination);

    List<Flight> findAllByStatusIn(List<FlightState> flightStates);
}
