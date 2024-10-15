package solvd.inc;

import solvd.inc.dao.implementation.TripStatusImpl;
import solvd.inc.model.Driver;
import solvd.inc.model.TripStatus;
import solvd.inc.model.Vehicle;
import solvd.inc.model.VehicleMaintenance;
import solvd.inc.service.DriverService;
import solvd.inc.service.VehicleMaintenanceService;
import solvd.inc.service.VehicleService;
import solvd.inc.service.impl.DriverServiceImpl;
import solvd.inc.service.impl.TripStatusServiceImpl;
import solvd.inc.service.impl.VehicleMaintenanceServiceImpl;
import solvd.inc.service.impl.VehicleServiceImpl;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        DriverService driverService = new DriverServiceImpl();
        VehicleService vehicleService = new VehicleServiceImpl();

        Optional<Vehicle> optionalVehicle = vehicleService.getVehicleById(3L);

        try {
            Vehicle vehicle = optionalVehicle.orElseThrow(() ->
                    new RuntimeException("Vehicle not found with ID: 3"));

            Driver newDriver = new Driver();
            newDriver.setLicenseNumber("XYZ123456er");
            newDriver.setFirstName("John");
            newDriver.setLastName("Carlson");
            newDriver.setRating(4.5);
            newDriver.setVehicle(vehicle);

            driverService.createDriver(newDriver);
            System.out.println("Driver created successfully: " + newDriver);

            Driver retrievedDriver = driverService.getDriverById(newDriver.getId()).orElseThrow();
            System.out.println("Retrieved Driver: " + retrievedDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
