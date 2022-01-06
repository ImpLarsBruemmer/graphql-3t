package de.inmediasp.graphql.operations;

import java.time.OffsetDateTime;

import de.inmediasp.graphql.persistence.FlightState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightInput {
    private Long id;
    private String start;
    private String destination;
    private OffsetDateTime arrival;
    private OffsetDateTime departure;
    private FlightState status;
}
