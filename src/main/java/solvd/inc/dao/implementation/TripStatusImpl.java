package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.GenericDAO;
import solvd.inc.dao.TripStatusDAO;
import solvd.inc.model.TripStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripStatusImpl implements TripStatusDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final TripStatusMapper tripStatusMapper = new TripStatusMapper();

    @Override
    public void create(TripStatus entity) {
        String query = "INSERT INTO trip_statuses (title) VALUES (?)";
        try(Connection connection = CONNECTION_POOL.acquireConnection().join();
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, entity.getTitle());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error during creating new TripStatus", e);
        }
    }

    @Override
    public Optional<TripStatus> getById(Long id) {
        String query = "SELECT * FROM trip_statuses WHERE trip_status_id = ?";
        TripStatus tripStatus = null;
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tripStatus = tripStatusMapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding TripStatus by ID", e);
        }
        return Optional.ofNullable(tripStatus);
    }

    @Override
    public List<TripStatus> findAll() {
        String query = "SELECT * FROM trip_statuses";
        List<TripStatus> tripStatuses = new ArrayList<>();
        try(Connection connection = CONNECTION_POOL.acquireConnection().join();
        PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                TripStatus tripStatus = tripStatusMapper.map(rs);
                tripStatuses.add(tripStatus);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error during finding all TripStatuses");
        }
        return tripStatuses;
    }

    @Override
    public void update(TripStatus entity) {
        String query = "UPDATE trip_statuses SET title = ? WHERE trip_status_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, entity.getTitle());
            ps.setLong(2, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating TripStatus", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM trip_statuses WHERE trip_status_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting TripStatus by ID", e);
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        String query = "SELECT COUNT(*) FROM trip_statuses WHERE title = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking for duplicate TripStatus title", e);
        }
        return false;
    }

}
