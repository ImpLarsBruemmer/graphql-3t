package de.inmediasp.graphql.operations;

import de.inmediasp.graphql.persistence.FlightState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private BoardType type;
    private String title;
    private String start;
    private String destination;
    private FlightState status;
}
