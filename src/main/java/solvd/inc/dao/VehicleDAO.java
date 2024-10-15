package solvd.inc.dao;

import solvd.inc.model.Vehicle;

public interface VehicleDAO extends GenericDAO<Vehicle> {
    boolean existsByPlateNumber(String plateNumber);
}
