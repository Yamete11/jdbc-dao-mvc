package solvd.inc.dao.implementation;

import solvd.inc.config.ConnectionPool;
import solvd.inc.dao.PaymentMethodDAO;
import solvd.inc.model.PaymentMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentMethodImpl implements PaymentMethodDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final PaymentMethodMapper paymentMethodMapper = new PaymentMethodMapper();

    @Override
    public void create(PaymentMethod entity) {
        String query = "INSERT INTO payment_methods (title, description) VALUES (?, ?)";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during creating new PaymentMethod", e);
        }
    }

    @Override
    public Optional<PaymentMethod> getById(Long id) {
        String query = "SELECT * FROM payment_methods WHERE payment_method_id = ?";
        PaymentMethod paymentMethod = null;
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                paymentMethod = paymentMethodMapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding PaymentMethod by ID", e);
        }
        return Optional.ofNullable(paymentMethod);
    }

    @Override
    public List<PaymentMethod> findAll() {
        String query = "SELECT * FROM payment_methods";
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PaymentMethod paymentMethod = paymentMethodMapper.map(rs);
                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during finding all PaymentMethods");
        }
        return paymentMethods;
    }

    @Override
    public void update(PaymentMethod entity) {
        String query = "UPDATE payment_methods SET title = ?, description = ? WHERE payment_method_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during updating PaymentMethod", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM payment_methods WHERE payment_method_id = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deleting PaymentMethod by ID", e);
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        String query = "SELECT COUNT(*) FROM payment_methods WHERE title = ?";
        try (Connection connection = CONNECTION_POOL.acquireConnection().join();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during checking existence of PaymentMethod by title", e);
        }
        return false;
    }
}