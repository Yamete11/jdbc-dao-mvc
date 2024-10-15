package solvd.inc.service.impl;

import solvd.inc.dao.GenericDAO;
import solvd.inc.dao.TripStatusDAO;
import solvd.inc.dao.implementation.TripStatusImpl;
import solvd.inc.dao.implementation.TripStatusMapper;
import solvd.inc.exception.TripStatusNotFoundException;
import solvd.inc.model.TripStatus;
import solvd.inc.service.TripStatusService;

import java.util.List;
import java.util.Optional;

public class TripStatusServiceImpl implements TripStatusService {

    private final TripStatusDAO tripStatusDAO;

    public TripStatusServiceImpl() {
        this.tripStatusDAO = new TripStatusImpl();
    }

    @Override
    public void createTripStatus(TripStatus tripStatus) {
        validateTripStatus(tripStatus);

        if (tripStatusDAO.existsByTitle(tripStatus.getTitle())) {
            throw new IllegalArgumentException("TripStatus with title '" + tripStatus.getTitle() + "' already exists");
        }

        tripStatusDAO.create(tripStatus);
    }

    @Override
    public Optional<TripStatus> getTripStatusById(Long id) {
        TripStatus tripStatus = tripStatusDAO.getById(id)
                .orElseThrow(() -> new TripStatusNotFoundException("TripStatus with id " + id + " not found"));
        return Optional.of(tripStatus);
    }


    @Override
    public List<TripStatus> getAllTripStatuses() {
        return tripStatusDAO.findAll();
    }

    @Override
    public void updateTripStatus(TripStatus tripStatus) {
        validateTripStatus(tripStatus);

        if (tripStatusDAO.existsByTitle(tripStatus.getTitle())) {
            throw new IllegalArgumentException("TripStatus with title '" + tripStatus.getTitle() + "' already exists");
        }
        tripStatusDAO.update(tripStatus);
    }

    @Override
    public void deleteTripStatusById(Long id) {
        Optional<TripStatus> tripStatus = tripStatusDAO.getById(id);
        if (tripStatus.isEmpty()) {
            throw new TripStatusNotFoundException("TripStatus with id " + id + " not found");
        }

        tripStatusDAO.deleteById(id);
    }


    private void validateTripStatus(TripStatus tripStatus) {
        if (tripStatus.getTitle() == null || tripStatus.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("TripStatus title cannot be null or empty");
        }
    }
}
