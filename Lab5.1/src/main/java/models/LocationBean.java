package models;


import database.LocationRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean(name = "locationBean")
@SessionScoped
public class LocationBean implements Serializable {
    private String locationName;
    private String locationID;

    private List<LocationBean> allLocations = new ArrayList<>();

    private final LocationRepository locationRepository = new LocationRepository();

    public LocationBean() {
    }

    public LocationBean( String locationID,String locationName) {
        this.locationName = locationName;
        this.locationID = locationID;
    }

    public void onClose()
    {
        this.locationName = null;
        this.locationID = null;
        addMessage("Cancel pressed");
    }

    public void save() {
        if (this.locationName == null) {
            return;
        }
        // to do : increment id
        Random random = new Random();
        this.locationID = String.valueOf(random.nextInt(10000000));

        locationRepository.addLocation(this);
        addMessage("Location " + locationName + " added to the database.");
        this.locationName = null;
        this.locationID = null;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public List<LocationBean> getAllLocations() {
        allLocations.clear();
        allLocations.addAll(locationRepository.getAllLocations());
        return allLocations;
    }
}