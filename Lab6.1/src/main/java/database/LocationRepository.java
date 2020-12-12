package database;

import models.LocationBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {

    public static final String LOCATION_ID_STRING = "locationID";
    public static final String LOCATION_NAME_STRING = "locationName";

    public void addLocation(LocationBean locationBean) {
        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.err.println("addLocation: null connection.");
            return;
        }
        // prepared statement is not implemented in the driver
        String sql = "INSERT INTO Locations ( \"locationID\", \"locationName\") VALUES ( \""
                + locationBean.getLocationID().trim() + "\", \"" + locationBean.getLocationName().trim() + "\");";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            dbConnection.closeConn();
            return;
        }
        System.out.println("Location added successfully.");
        dbConnection.closeConn();
    }

    public List<LocationBean> getAllLocations() {
        List<LocationBean> LocationBeanList = new ArrayList<>();

        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, cannot show all locations.");
        }
        String sql = "SELECT * FROM Locations;";
        try {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                LocationBeanList.add(new LocationBean(resultSet.getString(LOCATION_ID_STRING), resultSet.getString(LOCATION_NAME_STRING)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return LocationBeanList;
    }

    public LocationBean getNameById(String locationId) {
        LocationBean locationBean = null;
        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, cannot show all locations.");
        }
        String sql = "SELECT * FROM Locations where \"locationID\" LIKE \"" + locationId + "\";";
        try {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                locationBean = new LocationBean(resultSet.getString(LOCATION_ID_STRING), resultSet.getString(LOCATION_NAME_STRING));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return locationBean;
    }
}
