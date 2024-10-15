package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.VehicleDAO;
import solvd.inc.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleImpl implements VehicleDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    public void create(Vehicle entity) {
        String query = "INSERT INTO vehicles (model, year, plate_number) VALUES (?, ?, ?)";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getModel());
            ps.setInt(2, entity.getYear());
            ps.setString(3, entity.getPlateNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during creating a new Vehicle", e);
        }
    }


    @Override
    public Optional<Vehicle> getById(Long id) {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        Vehicle vehicle = null;
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                vehicle = vehicleMapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Vehicle by ID", e);
        }
        return Optional.ofNullable(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        String query = "SELECT * FROM vehicles";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = vehicleMapper.map(rs);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all Vehicles");
        }
        return vehicles;
    }

    @Override
    public void update(Vehicle entity) {
        String query = "UPDATE vehicles SET model = ?, year = ?, plate_number = ? WHERE vehicle_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, entity.getModel());
            ps.setInt(2, entity.getYear());
            ps.setString(3, entity.getPlateNumber());
            ps.setLong(4, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating Vehicle", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting Vehicle by ID", e);
        }
    }

    @Override
    public boolean existsByPlateNumber(String plateNumber) {
        String query = "SELECT COUNT(*) FROM vehicles WHERE plate_number = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, plateNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during checking existence of Vehicle by plate number", e);
        }
        return false;
    }
}
