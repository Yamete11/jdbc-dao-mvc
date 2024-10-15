package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndLocation {
    private Long id;
    private String city;
    private String street;
}
