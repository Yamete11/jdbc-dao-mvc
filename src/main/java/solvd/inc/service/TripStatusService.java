package solvd.inc.service;

import solvd.inc.model.TripStatus;

import java.util.List;
import java.util.Optional;

public interface TripStatusService {
    void createTripStatus(TripStatus tripStatus);
    Optional<TripStatus> getTripStatusById(Long id);
    List<TripStatus> getAllTripStatuses();
    void updateTripStatus(TripStatus tripStatus);
    void deleteTripStatusById(Long id);
}
