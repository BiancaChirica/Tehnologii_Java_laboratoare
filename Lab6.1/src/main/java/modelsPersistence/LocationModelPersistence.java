package modelsPersistence;

import javax.persistence.*;

@Entity
@Table(name = "Locations")
public class LocationModelPersistence {
    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "location_id_seq")
    @GeneratedValue(generator = "sequence")
    private String locationID;

    @Basic
    @Column(name = "locationName", nullable = false)
    private String locationName;

    public LocationModelPersistence(String locationName) {
        this.locationName = locationName;
    }

    public LocationModelPersistence() {
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
