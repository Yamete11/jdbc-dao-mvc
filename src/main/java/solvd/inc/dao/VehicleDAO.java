package solvd.inc.dao;

import solvd.inc.model.Vehicle;

import java.util.Optional;

public interface VehicleDAO extends GenericDAO<Vehicle> {
    boolean existsByPlateNumber(String plateNumber);
    Optional<Vehicle> getByPlateNumber(String plateNumber);
}
