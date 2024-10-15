package solvd.inc.dao;

import solvd.inc.model.TripStatus;

public interface TripStatusDAO extends GenericDAO<TripStatus> {
    boolean existsByTitle(String title);
}
