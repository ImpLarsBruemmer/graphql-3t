package de.inmediasp.graphql.operations;

import java.util.List;

import de.inmediasp.graphql.persistence.Flight;
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
    private List<Flight> flights;
}
