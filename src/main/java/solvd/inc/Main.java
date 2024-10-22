package solvd.inc;

import solvd.inc.dao.implementation.PaymentMethodImpl;
import solvd.inc.dao.implementation.TripStatusImpl;
import solvd.inc.model.*;
import solvd.inc.service.*;
import solvd.inc.service.impl.*;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        DriverService driverService = new DriverServiceImpl();
        VehicleService vehicleService = new VehicleServiceImpl();
        VehicleMaintenanceService vehicleMaintenanceService = new VehicleMaintenanceServiceImpl();
        TripStatusService tripStatus = new TripStatusServiceImpl();
        PaymentMethodService paymentMethod = new PaymentMethodServiceImpl();

        /*List<Driver> driverList = driverService.getAllDrivers();
        driverList.forEach(System.out::println);

        List<Vehicle> vehicleList = vehicleService.getAllVehicles();
        vehicleList.forEach(System.out::println);

        List<VehicleMaintenance> vehicleMaintenanceList = vehicleMaintenanceService.getAllVehicleMaintenances();
        vehicleMaintenanceList.forEach(System.out::println);

        List<TripStatus> tripStatusList = tripStatus.getAllTripStatuses();
        tripStatusList.forEach(System.out::println);

        List<PaymentMethod> paymentMethodList = paymentMethod.getAllPaymentMethods();
        paymentMethodList.forEach(System.out::println);*/



        /*System.out.println(driverService.getDriverById(1L));
        System.out.println(vehicleService.getVehicleById(1L));
        System.out.println(vehicleMaintenanceService.getVehicleMaintenanceById(1L));
        System.out.println(tripStatus.getTripStatusById(1L));
        System.out.println(paymentMethod.getPaymentMethodById(1L));*/

        /*System.out.println(paymentMethod.getPaymentMethodById(1L));

        Optional<PaymentMethod> optionalPaymentMethod = paymentMethod.getPaymentMethodById(1L);
        PaymentMethod paymentMethod1 = optionalPaymentMethod.get();
        paymentMethod1.setTitle("New title");

        paymentMethod.updatePaymentMethod(paymentMethod1);

        System.out.println(paymentMethod.getPaymentMethodById(1L));*/


        /*System.out.println(vehicleService.getVehicleById(1L));

        Optional<Vehicle> optionalVehicle = vehicleService.getVehicleById(1L);
        Vehicle vehicle = optionalVehicle.get();

        vehicle.setYear(2030);
        vehicleService.updateVehicle(vehicle);
        System.out.println(vehicleService.getVehicleById(1L));*/


        /*System.out.println(tripStatus.getAllTripStatuses());

        TripStatus tripStatus1 = new TripStatus();
        tripStatus1.setTitle("Update");

        tripStatus.createTripStatus(tripStatus1);*/

        System.out.println(tripStatus.getAllTripStatuses());

        tripStatus.deleteTripStatusById(8L);
        System.out.println(tripStatus.getAllTripStatuses());







    }
}
