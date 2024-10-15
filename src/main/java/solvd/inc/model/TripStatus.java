package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripStatus {
    private Long id;
    private String title;
    private List<Trip> trips;
}
