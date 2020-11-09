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

public class MeetingRepository {
    public static final String MEETING_ID_STRING = "meetingID";
    public static final String MEETING_TOPIC_STRING = "topic";
    public static final String MEETING_STARTING_STRING = "startingTime";
    public static final String MEETING_DURATION_STRING = "duration";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void addMeeting(MeetingBean meetingBean) {
        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.err.println("addMeeting: null connection.");
            return;
        }
        // prepared statement is not implemented in the driver
        String sql = "INSERT INTO Meetings (\"meetingID\", \"topic\", \"startingTime\", \"duration\") VALUES ( \""
                + meetingBean.getMeetingID().trim() + "\", \""
                + meetingBean.getTopic().trim() + "\", \""
                + simpleDateFormat.format(meetingBean.getStartDate()) + "\", \""
                + String.valueOf(meetingBean.getDuration()) + "\");";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            dbConnection.closeConn();
            return;
        }
        System.out.println("Meeting added successfully.");
        dbConnection.closeConn();
    }

    public List<MeetingBean> getAllMeetings() {
        List<MeetingBean> meetingBeanList = new ArrayList<>();

        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, cannot show all meetings.");
        }
        String sql = "SELECT * FROM Meetings;";
        try {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                meetingBeanList.add(new MeetingBean(
                        resultSet.getString(MEETING_ID_STRING),
                        resultSet.getString(MEETING_TOPIC_STRING),
                        convertStringToDate(resultSet.getString(MEETING_STARTING_STRING)),
                        resultSet.getInt(MEETING_DURATION_STRING)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return meetingBeanList;
    }

    public List<MeetingBean> getMeetingsById(List<String> meetingsID) {
        List<MeetingBean> meetingBeanList = new ArrayList<>();
        String idList = "";
        for (int count = 0; count < meetingsID.size(); count++) {
            idList = "'" + meetingsID.get(count).trim() + "'";

            if (count != (meetingsID.size() - 1))
                idList += ",";
        }
        System.out.println(idList);

        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, return meetings.");
        }
        String sql = "SELECT * FROM Meetings WHERE meetingID IN (" + idList + ");";
        try {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                meetingBeanList.add(new MeetingBean(
                        resultSet.getString(MEETING_ID_STRING),
                        resultSet.getString(MEETING_TOPIC_STRING),
                        convertStringToDate(resultSet.getString(MEETING_STARTING_STRING)),
                        resultSet.getInt(MEETING_DURATION_STRING)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return meetingBeanList;
    }

    public Date convertStringToDate(String stringDate) {
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
