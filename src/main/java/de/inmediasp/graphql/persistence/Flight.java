package de.inmediasp.graphql.persistence;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String start;
    private String destination;

    private OffsetDateTime arrival;
    private OffsetDateTime departure;

    @Enumerated(EnumType.STRING)
    private FlightState status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "xref_flight_passenger",
            joinColumns = {@JoinColumn(name = "flight_id")},
            inverseJoinColumns = {@JoinColumn(name = "passenger_id")}
    )
    private List<Passenger> passengers;
}
