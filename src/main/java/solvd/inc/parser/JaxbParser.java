package solvd.inc.parser;

import solvd.inc.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbParser {

    public static TaxiCompany unmarshalTaxiCompany(String xmlFilePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TaxiCompany.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (TaxiCompany) unmarshaller.unmarshal(new File(xmlFilePath));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printTaxiCompanyData(TaxiCompany taxiCompany) {
        System.out.println("taxiCompany: ");


        List<Driver> drivers = taxiCompany.getDrivers();
        if (drivers != null) {
            for (Driver driver : drivers) {
                System.out.println("    driver: ");
                System.out.println("        id: " + driver.getId());
                System.out.println("        licenseNumber: " + driver.getLicenseNumber());
                System.out.println("        firstName: " + driver.getFirstName());
                System.out.println("        lastName: " + driver.getLastName());
                System.out.println("        rating: " + driver.getRating());

                if (driver.getVehicle() != null) {
                    System.out.println("        vehicle: ");
                    System.out.println("            id: " + driver.getVehicle().getId());
                    System.out.println("            model: " + driver.getVehicle().getModel());
                    System.out.println("            year: " + driver.getVehicle().getYear());
                    System.out.println("            plateNumber: " + driver.getVehicle().getPlateNumber());

                    List<VehicleMaintenance> maintenances = driver.getVehicle().getVehicleMaintenances();
                    if (maintenances != null && !maintenances.isEmpty()) {
                        System.out.println("            maintenances: ");
                        for (VehicleMaintenance maintenance : maintenances) {
                            System.out.println("                maintenance: ");
                            System.out.println("                    id: " + maintenance.getId());
                            System.out.println("                    date: " + maintenance.getDate());
                            System.out.println("                    description: " + maintenance.getDescription());
                        }
                    } else {
                        System.out.println("            maintenances: None");
                    }
                }
            }
        }

        List<TripStatus> tripStatuses = taxiCompany.getTripStatuses();
        if (tripStatuses != null) {
            for (TripStatus tripStatus : tripStatuses) {
                System.out.println("tripStatus: ");
                System.out.println("    id: " + tripStatus.getId());
                System.out.println("    title: " + tripStatus.getTitle());
            }
        }

        List<PaymentMethod> paymentMethods = taxiCompany.getPaymentMethods();
        if (paymentMethods != null) {
            for (PaymentMethod paymentMethod : paymentMethods) {
                System.out.println("paymentMethod: ");
                System.out.println("    id: " + paymentMethod.getId());
                System.out.println("    title: " + paymentMethod.getTitle());
                System.out.println("    description: " + paymentMethod.getDescription());
            }
        }
    }




}
