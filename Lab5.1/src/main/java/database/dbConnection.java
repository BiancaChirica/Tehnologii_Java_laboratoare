package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {
    private static Connection conn = null;

    public static Connection getConn() {
        if (conn == null)
            conn = createConn();
        return conn;
    }

    private static Connection createConn() {
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            String url = "jdbc:sqlite:C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab5.1\\mySqlite.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            conn = null;
        }

        return conn;

    }

    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn = null;
        }
    }

    public static void createTables() {
        Connection connection = getConn();

        String sql = "DROP TABLE Persons;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Persons (personID varchar(255) PRIMARY KEY, name varchar(255) NOT NULL);";
        executeQuery(connection, sql);
        System.out.println("Persons tables created.");

        sql = "DROP TABLE Meetings;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Meetings (meetingID varchar(255) PRIMARY KEY, topic varchar(255), startingTime varchar(255) , duration int, locationId varchar(255));";
        executeQuery(connection, sql);
        System.out.println("Meetings tables created.");

        sql = "DROP TABLE Mapping;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Mapping (meetingID varchar(255), personID varchar(255) );";
        executeQuery(connection, sql);
        System.out.println("Mapping tables created.");

        sql = "DROP TABLE Locations;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Locations (locationID varchar(255) PRIMARY KEY, locationName varchar(255) NOT NULL);";
        executeQuery(connection, sql);
        System.out.println("Locations tables created.");

        dbConnection.closeConn();
    }

    private static void executeQuery(Connection connection, String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            conn = null;
        }
    }

    public static void main(String[] args) {
        dbConnection.createTables();
        dbConnection.closeConn();
    }
}