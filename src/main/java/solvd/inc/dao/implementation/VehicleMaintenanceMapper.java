package solvd.inc.dao.implementation;

import solvd.inc.model.VehicleMaintenance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleMaintenanceMapper {

    public VehicleMaintenance map(ResultSet rs) throws SQLException {
        VehicleMaintenance maintenance = new VehicleMaintenance();
        maintenance.setId(rs.getLong("maintenance_id"));
        maintenance.setDate(rs.getTimestamp("maintenance_date"));
        maintenance.setDescription(rs.getString("description"));
        return maintenance;
    }
}
