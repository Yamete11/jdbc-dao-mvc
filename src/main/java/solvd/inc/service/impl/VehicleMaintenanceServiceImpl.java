package solvd.inc.service.impl;

import solvd.inc.dao.VehicleMaintenanceDAO;
import solvd.inc.dao.implementation.VehicleMaintenanceImpl;
import solvd.inc.model.VehicleMaintenance;
import solvd.inc.service.VehicleMaintenanceService;

import java.util.List;
import java.util.Optional;

public class VehicleMaintenanceServiceImpl implements VehicleMaintenanceService {

    private final VehicleMaintenanceDAO vehicleMaintenanceDAO;

    public VehicleMaintenanceServiceImpl() {
        this.vehicleMaintenanceDAO = new VehicleMaintenanceImpl();
    }

    @Override
    public void createVehicleMaintenance(VehicleMaintenance maintenance) {
        validateVehicleMaintenance(maintenance);
        vehicleMaintenanceDAO.create(maintenance);
    }

    @Override
    public Optional<VehicleMaintenance> getVehicleMaintenanceById(Long id) {
        VehicleMaintenance maintenance = vehicleMaintenanceDAO.getById(id)
                .orElseThrow(() -> new RuntimeException("VehicleMaintenance with id " + id + " not found"));
        return Optional.of(maintenance);
    }

    @Override
    public List<VehicleMaintenance> getAllVehicleMaintenances() {
        return vehicleMaintenanceDAO.findAll();
    }

    @Override
    public void updateVehicleMaintenance(VehicleMaintenance maintenance) {
        validateVehicleMaintenance(maintenance);
        vehicleMaintenanceDAO.update(maintenance);
    }

    @Override
    public void deleteVehicleMaintenanceById(Long id) {
        vehicleMaintenanceDAO.deleteById(id);
    }

    private void validateVehicleMaintenance(VehicleMaintenance maintenance) {
        if (maintenance.getDescription() == null || maintenance.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("VehicleMaintenance description cannot be null or empty");
        }
        if (maintenance.getDate() == null) {
            throw new IllegalArgumentException("VehicleMaintenance date cannot be null");
        }
    }
}
