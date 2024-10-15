package solvd.inc.service;

import solvd.inc.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    void createVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicleById(Long id);
    List<Vehicle> getAllVehicles();
    void updateVehicle(Vehicle vehicle);
    void deleteVehicleById(Long id);
}
