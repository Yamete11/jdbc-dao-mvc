package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.DriverDAO;
import solvd.inc.model.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverImpl implements DriverDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final DriverMapper driverMapper = new DriverMapper();

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
        String query = "SELECT * FROM drivers WHERE driver_id = ?";
        Driver driver = null;
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                driver = driverMapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Driver by ID", e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public List<Driver> findAll() {
        String query = "SELECT * FROM drivers";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Driver driver = driverMapper.map(rs);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all Drivers");
        }
        return drivers;
    }

    @Override
    public void update(Driver entity) {
        String query = "UPDATE drivers SET license_number = ?, first_name = ?, last_name = ?, rating = ? WHERE driver_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, entity.getLicenseNumber());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setDouble(4, entity.getRating());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating Driver", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting Driver by ID", e);
        }
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        String query = "SELECT COUNT(*) FROM drivers WHERE license_number = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, licenseNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during checking Driver by license number", e);
        }
        return false;
    }
}
