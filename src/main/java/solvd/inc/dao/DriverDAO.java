package solvd.inc.dao;

import solvd.inc.model.Driver;

public interface DriverDAO extends GenericDAO<Driver> {
    boolean existsByLicenseNumber(String licenseNumber);
}
