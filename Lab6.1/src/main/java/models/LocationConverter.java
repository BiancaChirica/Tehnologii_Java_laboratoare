package models;

import database.LocationRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "locationConverter")
public class LocationConverter implements Converter {
    LocationRepository locationRepository = new LocationRepository();

    @Override
    public String getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String locationId = (String) o;
        return locationRepository.getNameById(locationId).getLocationName();
    }

}
