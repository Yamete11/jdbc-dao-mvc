package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.DriverDAO;
import solvd.inc.model.Driver;
import solvd.inc.model.Vehicle;
import solvd.inc.model.VehicleMaintenance;

import java.sql.*;
import java.util.*;

public class DriverImpl implements DriverDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final DriverMapper driverMapper = new DriverMapper();
    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    public void create(Driver entity) {
        String query = "INSERT INTO drivers (license_number, first_name, last_name, rating, vehicle_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getLicenseNumber());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setDouble(4, entity.getRating());
            ps.setDouble(5, entity.getVehicle().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during creating new Driver", e);
        }
    }

    @Override
    public Optional<Driver> getById(Long id) {
        String query = "SELECT d.driver_id, d.license_number, d.first_name, d.last_name, d.rating, " +
                "v.vehicle_id, v.model, v.year, v.plate_number, " +
                "vm.maintenance_id, vm.maintenance_date, vm.description " +
                "FROM drivers d " +
                "LEFT JOIN vehicles v ON d.vehicle_id = v.vehicle_id " +
                "LEFT JOIN vehicle_maintenances vm ON v.vehicle_id = vm.vehicle_id " +
                "WHERE d.driver_id = ?";

        Driver driver = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    driver = driverMapper.map(rs);
                    if (rs.getLong("vehicle_id") != 0) {
                        Vehicle vehicle = vehicleMapper.map(rs);
                        driver.setVehicle(vehicle);
                    }

                    do {
                        long maintenanceId = rs.getLong("maintenance_id");
                        if (maintenanceId != 0) {
                            VehicleMaintenance maintenance = new VehicleMaintenance();
                            maintenance.setId(maintenanceId);
                            maintenance.setDate(rs.getTimestamp("maintenance_date"));
                            maintenance.setDescription(rs.getString("description"));

                            if (driver.getVehicle() != null) {
                                if (driver.getVehicle().getVehicleMaintenances() == null) {
                                    driver.getVehicle().setVehicleMaintenances(new ArrayList<>());
                                }
                                driver.getVehicle().getVehicleMaintenances().add(maintenance);
                            }
                        }
                    } while (rs.next());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Driver by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(driver);
    }




    @Override
    public List<Driver> findAll() {
        String query = "SELECT d.driver_id, d.license_number, d.first_name, d.last_name, d.rating, " +
                "v.vehicle_id, v.model, v.year, v.plate_number, " +
                "vm.maintenance_id, vm.maintenance_date, vm.description " +
                "FROM drivers d " +
                "LEFT JOIN vehicles v ON d.vehicle_id = v.vehicle_id " +
                "LEFT JOIN vehicle_maintenances vm ON v.vehicle_id = vm.vehicle_id";

        List<Driver> drivers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                Map<Long, Driver> driverMap = new HashMap<>();

                while (rs.next()) {
                    long driverId = rs.getLong("driver_id");
                    Driver driver = driverMap.get(driverId);

                    if (driver == null) {
                        driver = driverMapper.map(rs);
                        driverMap.put(driverId, driver);
                    }

                    if (rs.getLong("vehicle_id") != 0) {
                        Vehicle vehicle = vehicleMapper.map(rs);
                        driver.setVehicle(vehicle);
                    }

                    long maintenanceId = rs.getLong("maintenance_id");
                    if (maintenanceId != 0) {
                        VehicleMaintenance maintenance = new VehicleMaintenance();
                        maintenance.setId(maintenanceId);
                        maintenance.setDate(rs.getTimestamp("maintenance_date"));
                        maintenance.setDescription(rs.getString("description"));

                        if (driver.getVehicle() != null) {
                            if (driver.getVehicle().getVehicleMaintenances() == null) {
                                driver.getVehicle().setVehicleMaintenances(new ArrayList<>());
                            }
                            driver.getVehicle().getVehicleMaintenances().add(maintenance);
                        }
                    }
                }

                drivers.addAll(driverMap.values());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all Drivers", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return drivers;
    }





    @Override
    public void update(Driver entity) {
        String query = "UPDATE drivers SET license_number = ?, first_name = ?, last_name = ?, rating = ? WHERE driver_id = ?";
        Connection connection = null;

        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, entity.getLicenseNumber());
                ps.setString(2, entity.getFirstName());
                ps.setString(3, entity.getLastName());
                ps.setDouble(4, entity.getRating());
                ps.setLong(5, entity.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating Driver", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }



    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        Connection connection = null;

        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting Driver by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }


    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        String query = "SELECT COUNT(*) FROM drivers WHERE license_number = ?";
        Connection connection = null;

        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, licenseNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during checking Driver by license number", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return false;
    }


    @Override
    public Optional<Driver> getByLicenseNumber(String licenseNumber) {
        String query = "SELECT * FROM drivers WHERE license_number = ?";
        Driver driver = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                ps.setString(1, licenseNumber);

                if (rs.next()) {
                    driver = driverMapper.map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Driver by license number", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(driver);
    }



}
