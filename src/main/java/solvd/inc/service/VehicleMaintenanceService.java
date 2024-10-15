package solvd.inc.service;

import solvd.inc.model.VehicleMaintenance;

import java.util.List;
import java.util.Optional;

public interface VehicleMaintenanceService {
    void createVehicleMaintenance(VehicleMaintenance maintenance);
    Optional<VehicleMaintenance> getVehicleMaintenanceById(Long id);
    List<VehicleMaintenance> getAllVehicleMaintenances();
    void updateVehicleMaintenance(VehicleMaintenance maintenance);
    void deleteVehicleMaintenanceById(Long id);
}
