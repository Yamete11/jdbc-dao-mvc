package solvd.inc.dao.implementation;

import solvd.inc.model.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleMapper {

    public Vehicle map(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getLong("vehicle_id"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setPlateNumber(rs.getString("plate_number"));
        return vehicle;
    }
}
