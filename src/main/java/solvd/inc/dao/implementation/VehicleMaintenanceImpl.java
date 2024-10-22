package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.VehicleMaintenanceDAO;
import solvd.inc.model.VehicleMaintenance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleMaintenanceImpl implements VehicleMaintenanceDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final VehicleMaintenanceMapper vehicleMaintenanceMapper = new VehicleMaintenanceMapper();

    @Override
    public void create(VehicleMaintenance entity) {
        String query = "INSERT INTO vehicle_maintenance (date, description) VALUES (?, ?)";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setTimestamp(1, (Timestamp) entity.getDate());
                ps.setString(2, entity.getDescription());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during creating new VehicleMaintenance", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public Optional<VehicleMaintenance> getById(Long id) {
        String query = "SELECT * FROM vehicle_maintenances WHERE maintenance_id = ?";
        VehicleMaintenance maintenance = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        maintenance = vehicleMaintenanceMapper.map(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding VehicleMaintenance by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(maintenance);
    }

    @Override
    public List<VehicleMaintenance> findAll() {
        String query = "SELECT * FROM vehicle_maintenances";
        List<VehicleMaintenance> maintenances = new ArrayList<>();
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    VehicleMaintenance maintenance = vehicleMaintenanceMapper.map(rs);
                    maintenances.add(maintenance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all VehicleMaintenances", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return maintenances;
    }

    @Override
    public void update(VehicleMaintenance entity) {
        String query = "UPDATE vehicle_maintenances SET date = ?, description = ? WHERE maintenance_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setTimestamp(1, (Timestamp) entity.getDate());
                ps.setString(2, entity.getDescription());
                ps.setLong(3, entity.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating VehicleMaintenance", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM vehicle_maintenances WHERE maintenance_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting VehicleMaintenance by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }
}
