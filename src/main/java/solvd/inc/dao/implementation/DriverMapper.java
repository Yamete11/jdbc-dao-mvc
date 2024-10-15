package solvd.inc.dao.implementation;

import solvd.inc.model.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper {

    public Driver map(ResultSet rs) throws SQLException {
        Driver driver = new Driver();
        driver.setId(rs.getLong("driver_id"));
        driver.setLicenseNumber(rs.getString("license_number"));
        driver.setFirstName(rs.getString("first_name"));
        driver.setLastName(rs.getString("last_name"));
        driver.setRating(rs.getDouble("rating"));
        return driver;
    }
}
