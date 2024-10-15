package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Long id;
    private String licenseNumber;
    private String firstName;
    private String lastName;
    private double rating;
    private Vehicle vehicle;
}
