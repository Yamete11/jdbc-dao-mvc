package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private Long id;
    private double duration;
    private double distance;
    private Payment payment;
    private EndLocation endLocation;
    private StartLocation startLocation;
}
