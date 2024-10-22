package solvd.inc.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vehicle")
public class Vehicle {

    private Long id;
    private String model;
    private int year;
    private String plateNumber;
    private List<VehicleMaintenance> vehicleMaintenances;

    public Vehicle() {}

    public Vehicle(Long id, String model, int year, String plateNumber, List<VehicleMaintenance> vehicleMaintenances) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.plateNumber = plateNumber;
        this.vehicleMaintenances = vehicleMaintenances;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @XmlElement(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @XmlElement(name = "plateNumber")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @XmlElementWrapper(name = "vehicleMaintenances")
    @XmlElement(name = "maintenance")
    public List<VehicleMaintenance> getVehicleMaintenances() {
        return vehicleMaintenances;
    }

    public void setVehicleMaintenances(List<VehicleMaintenance> vehicleMaintenances) {
        this.vehicleMaintenances = vehicleMaintenances;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", plateNumber='" + plateNumber + '\'' +
                ", vehicleMaintenances=" + vehicleMaintenances +
                '}';
    }
}
