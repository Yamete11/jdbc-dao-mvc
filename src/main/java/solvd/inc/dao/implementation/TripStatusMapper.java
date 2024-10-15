package solvd.inc.dao.implementation;

import solvd.inc.model.TripStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripStatusMapper {

    public TripStatus map(ResultSet rs) throws SQLException {
        TripStatus tripStatus = new TripStatus();
        tripStatus.setId(rs.getLong("trip_status_id"));
        tripStatus.setTitle(rs.getString("title"));

        return tripStatus;
    }
}
