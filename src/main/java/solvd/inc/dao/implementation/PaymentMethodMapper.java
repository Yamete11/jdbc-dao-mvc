package solvd.inc.dao.implementation;

import solvd.inc.model.PaymentMethod;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMethodMapper {

    public PaymentMethod map(ResultSet rs) throws SQLException {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(rs.getLong("payment_method_id"));
        paymentMethod.setTitle(rs.getString("title"));
        paymentMethod.setDescription(rs.getString("description"));
        return paymentMethod;
    }
}
