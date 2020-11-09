package database;

import models.MeetingBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MappingRepository {
    public static final String MEETING_ID_STRING = "meetingID";
    public static final String PERSON_ID_STRING = "personID";


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void addMapping(String meetingID, String personID) {
        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.err.println("addMapping: null connection.");
            return;
        }
        // prepared statement is not implemented in the driver
        String sql = "INSERT INTO Mapping (\"meetingID\", \"personID\") VALUES ( \""
                + meetingID + "\", \"" + personID + "\");";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            dbConnection.closeConn();
            return;
        }
        System.out.println("Meeting added successfully to mapping.");
        dbConnection.closeConn();
    }

    public List<String> getAllMeetingsForAPerson(String personID) {
        List<String> meetingIdBeanList = new ArrayList<>();

        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, cannot show all meetings for person.");
        }
        String sql = "SELECT * FROM Mapping WHERE personID LIKE '" + personID + "';";
        try {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                meetingIdBeanList.add(resultSet.getString(MEETING_ID_STRING));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return meetingIdBeanList;
    }
}
