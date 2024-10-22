package solvd.inc.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "taxiCompany")
public class TaxiCompany {

    private List<TripStatus> tripStatuses;
    private List<PaymentMethod> paymentMethods;
    private List<Driver> drivers;

    public TaxiCompany() {}


    public List<TripStatus> getTripStatuses() {
        return tripStatuses;
    }
    @XmlElement(name = "tripStatus")
    public void setTripStatuses(List<TripStatus> tripStatuses) {
        this.tripStatuses = tripStatuses;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
    @XmlElement(name = "paymentMethod")
    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @XmlElement(name = "driver")
    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
