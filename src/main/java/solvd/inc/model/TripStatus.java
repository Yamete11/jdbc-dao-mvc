package solvd.inc.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tripStatus")
public class TripStatus {

    private Long id;
    private String title;

    public TripStatus() {}

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TripStatus{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
