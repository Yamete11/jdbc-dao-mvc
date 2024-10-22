package solvd.inc.service.impl;

import solvd.inc.dao.DriverDAO;
import solvd.inc.dao.implementation.DriverImpl;
import solvd.inc.model.Driver;
import solvd.inc.service.DriverService;

import java.util.List;
import java.util.Optional;


public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;

    public DriverServiceImpl() {
        this.driverDAO = new DriverImpl();
    }

    @Override
    public void createDriver(Driver driver) {
        validateDriver(driver);

        if (driverDAO.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new IllegalArgumentException("Driver with license number '" + driver.getLicenseNumber() + "' already exists");
        }

        driverDAO.create(driver);
    }

    @Override
    public Optional<Driver> getDriverById(Long id) {
        Driver driver = driverDAO.getById(id)
                .orElseThrow(() -> new RuntimeException("Driver with id " + id + " not found"));
        return Optional.of(driver);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverDAO.findAll();
    }

    @Override
    public void updateDriver(Driver driver) {
        validateDriver(driver);

        String licenseNumber = driver.getLicenseNumber();
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Driver license number cannot be null or empty");
        }

        Optional<Driver> existingDriverOpt = driverDAO.getByLicenseNumber(licenseNumber);

        if (existingDriverOpt.isPresent()) {
            Driver existingDriver = existingDriverOpt.get();
            if (!existingDriver.getId().equals(driver.getId())) {
                throw new IllegalArgumentException("Driver with license number '" + licenseNumber + "' already exists");
            }
        }

        driverDAO.update(driver);
    }



    @Override
    public void deleteDriverById(Long id) {
        driverDAO.deleteById(id);
    }

    private void validateDriver(Driver driver) {
        if (driver.getLicenseNumber() == null || driver.getLicenseNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Driver license number cannot be null or empty");
        }
        if (driver.getFirstName() == null || driver.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("Driver first name cannot be null or empty");
        }
        if (driver.getLastName() == null || driver.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Driver last name cannot be null or empty");
        }
    }
}
