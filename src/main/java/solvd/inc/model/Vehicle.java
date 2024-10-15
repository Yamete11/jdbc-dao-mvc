package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Long id;
    private String model;
    private int year;
    private String plateNumber;
    private List<VehicleMaintenance> vehicleMaintenances;
}