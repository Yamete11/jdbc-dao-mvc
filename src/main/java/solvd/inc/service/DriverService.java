package solvd.inc.service;

import solvd.inc.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    void createDriver(Driver driver);
    Optional<Driver> getDriverById(Long id);
    List<Driver> getAllDrivers();
    void updateDriver(Driver driver);
    void deleteDriverById(Long id);
}
