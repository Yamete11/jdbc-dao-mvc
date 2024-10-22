package solvd.inc.service.impl;

import solvd.inc.dao.VehicleDAO;
import solvd.inc.dao.implementation.VehicleImpl;
import solvd.inc.model.Vehicle;
import solvd.inc.service.VehicleService;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleDAO vehicleDAO;

    public VehicleServiceImpl() {
        this.vehicleDAO = new VehicleImpl();
    }

    @Override
    public void createVehicle(Vehicle vehicle) {
        validateVehicle(vehicle);

        if (vehicleDAO.existsByPlateNumber(vehicle.getPlateNumber())) {
            throw new IllegalArgumentException("Vehicle with plate number '" + vehicle.getPlateNumber() + "' already exists");
        }

        vehicleDAO.create(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        Vehicle vehicle = vehicleDAO.getById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle with id " + id + " not found"));
        return Optional.of(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.findAll();
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        validateVehicle(vehicle);

        Optional<Vehicle> existingPlateNumber = vehicleDAO.getByPlateNumber(vehicle.getPlateNumber());
        if (existingPlateNumber.isPresent()) {
            Vehicle existingVehicle = existingPlateNumber.get();

            if (!existingVehicle.getId().equals(vehicle.getId())) {
                throw new IllegalArgumentException("Vehicle with the plate number '" + vehicle.getPlateNumber() + "' already exists");
            }
        }
        vehicleDAO.update(vehicle);
    }



    @Override
    public void deleteVehicleById(Long id) {
        vehicleDAO.deleteById(id);
    }

    private void validateVehicle(Vehicle vehicle) {
        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle model cannot be null or empty");
        }
        if (vehicle.getPlateNumber() == null || vehicle.getPlateNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle plate number cannot be null or empty");
        }
    }
}
