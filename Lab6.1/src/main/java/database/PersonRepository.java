package database;

import models.PersonBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    public static final String PERSON_ID_STRING = "personID";
    public static final String PERSON_NAME_STRING = "name";

    public void addPerson(PersonBean personBean) {
        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.err.println("addPerson: null connection.");
            return;
        }
        // prepared statement is not implemented in the driver
        String sql = "INSERT INTO Persons ( \"personID\", \"name\") VALUES ( \""
                + personBean.getPersonID().trim() + "\", \"" + personBean.getName().trim() + "\");";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            dbConnection.closeConn();
            return;
        }
        System.out.println("Person added successfully.");
        dbConnection.closeConn();
    }

    public List<PersonBean> getAllPersons() {
        List<PersonBean> personBeanList = new ArrayList<>();

        Connection connection = dbConnection.getConn();
        if (connection == null) {
            System.out.println("Connection was null, cannot show all persons.");
        }
        String sql = "SELECT * FROM Persons;";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                personBeanList.add(new PersonBean(resultSet.getString(PERSON_ID_STRING), resultSet.getString(PERSON_NAME_STRING)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbConnection.closeConn();
        return personBeanList;
    }
}
