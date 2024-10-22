package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "trip")
public class Trip {
    private Long id;
    private double duration;
    private double distance;
    private Payment payment;
    private EndLocation endLocation;
    private StartLocation startLocation;
}
