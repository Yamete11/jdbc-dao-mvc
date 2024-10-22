package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.VehicleDAO;
import solvd.inc.model.Vehicle;
import solvd.inc.model.VehicleMaintenance;

import java.sql.*;
import java.util.*;

public class VehicleImpl implements VehicleDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    public void create(Vehicle entity) {
        String query = "INSERT INTO vehicles (model, year, plate_number) VALUES (?, ?, ?)";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, entity.getModel());
                ps.setInt(2, entity.getYear());
                ps.setString(3, entity.getPlateNumber());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during creating a new Vehicle", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public Optional<Vehicle> getById(Long id) {
        String query = "SELECT v.vehicle_id, v.model, v.year, v.plate_number, " +
                "vm.maintenance_id, vm.maintenance_date, vm.description " +
                "FROM vehicles v " +
                "LEFT JOIN vehicle_maintenances vm ON v.vehicle_id = vm.vehicle_id " +
                "WHERE v.vehicle_id = ?";

        Vehicle vehicle = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    vehicle = vehicleMapper.map(rs);

                    do {
                        long maintenanceId = rs.getLong("maintenance_id");
                        if (maintenanceId != 0) {
                            VehicleMaintenance maintenance = new VehicleMaintenance();
                            maintenance.setId(maintenanceId);
                            maintenance.setDate(rs.getTimestamp("maintenance_date"));
                            maintenance.setDescription(rs.getString("description"));

                            if (vehicle.getVehicleMaintenances() == null) {
                                vehicle.setVehicleMaintenances(new ArrayList<>());
                            }
                            vehicle.getVehicleMaintenances().add(maintenance);
                        }
                    } while (rs.next());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Vehicle by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(vehicle);
    }


    @Override
    public List<Vehicle> findAll() {
        String query = "SELECT v.vehicle_id, v.model, v.year, v.plate_number, " +
                "vm.maintenance_id, vm.maintenance_date, vm.description " +
                "FROM vehicles v " +
                "LEFT JOIN vehicle_maintenances vm ON v.vehicle_id = vm.vehicle_id";

        List<Vehicle> vehicles = new ArrayList<>();
        Map<Long, Vehicle> vehicleMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    long vehicleId = rs.getLong("vehicle_id");
                    Vehicle vehicle = vehicleMap.get(vehicleId);

                    if (vehicle == null) {
                        vehicle = vehicleMapper.map(rs);
                        vehicleMap.put(vehicleId, vehicle);
                    }

                    long maintenanceId = rs.getLong("maintenance_id");
                    if (maintenanceId != 0) {
                        VehicleMaintenance maintenance = new VehicleMaintenance();
                        maintenance.setId(maintenanceId);
                        maintenance.setDate(rs.getTimestamp("maintenance_date"));
                        maintenance.setDescription(rs.getString("description"));

                        if (vehicle.getVehicleMaintenances() == null) {
                            vehicle.setVehicleMaintenances(new ArrayList<>());
                        }
                        vehicle.getVehicleMaintenances().add(maintenance);
                    }
                }

                vehicles.addAll(vehicleMap.values());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all Vehicles", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return vehicles;
    }


    @Override
    public void update(Vehicle entity) {
        String query = "UPDATE vehicles SET model = ?, year = ?, plate_number = ? WHERE vehicle_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, entity.getModel());
                ps.setInt(2, entity.getYear());
                ps.setString(3, entity.getPlateNumber());
                ps.setLong(4, entity.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating Vehicle", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting Vehicle by ID", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public boolean existsByPlateNumber(String plateNumber) {
        String query = "SELECT COUNT(*) FROM vehicles WHERE plate_number = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, plateNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during checking existence of Vehicle by plate number", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return false;
    }

    @Override
    public Optional<Vehicle> getByPlateNumber(String plateNumber) {
        String query = "SELECT * FROM vehicles WHERE plate_number = ?";
        Vehicle vehicle = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.acquireConnection().join();
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, plateNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        vehicle = vehicleMapper.map(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding Vehicle by plate number", e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(vehicle);
    }
}
