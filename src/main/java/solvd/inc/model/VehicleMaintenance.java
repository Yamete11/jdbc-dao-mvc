package solvd.inc.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMaintenance {
    private Long id;
    private Timestamp date;
    private String description;
}
