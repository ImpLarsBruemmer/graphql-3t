package de.inmediasp.graphql.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findAllByFlight(Flight flight);
}
