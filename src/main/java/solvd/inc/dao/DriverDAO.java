package solvd.inc.dao;

import solvd.inc.model.Driver;

import java.util.Optional;

public interface DriverDAO extends GenericDAO<Driver> {
    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Driver> getByLicenseNumber(String licenseNumber);
}
