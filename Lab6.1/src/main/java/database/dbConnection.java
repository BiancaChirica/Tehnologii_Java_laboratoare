package database;

import org.sqlite.SQLiteDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {
    private static Connection conn = null;
    private static Boolean dbConnectionVar = false;

    public static void bindDatabase() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        System.setProperty(Context.PROVIDER_URL,
                "ldap://localhost:1099");
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:comp/env");
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab6.1\\mySqlite.db");
            envContext.bind("jdbc/sqlite", ds);
            dbConnectionVar = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection to the database
     *
     * @return : connection object
     */
    public static Connection getConn() {
        if (dbConnectionVar == null) {
            bindDatabase();
        }

        Context ctx;
        DataSource ds;
        Connection connection = null;
        try {
            ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:comp/env");
            ds = (DataSource) envContext.lookup("/jdbc/sqlite");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * Function for the old connection from lab 4
     */

    private static Connection createConn() {
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            String url = "jdbc:sqlite:C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab6.1\\mySqlite.db";
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

    /**
     * Function to create the tables for the database
     */
    public static void createTables() {
        Connection connection = dbConnection.getConn();

        String sql = "DROP TABLE Persons;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Persons (personID varchar(255) PRIMARY KEY, name varchar(255) NOT NULL);";
        executeQuery(connection, sql);
        System.out.println("Persons tables created.");

        sql = "DROP TABLE Meetings;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Meetings (meetingID varchar(255) PRIMARY KEY, topic varchar(255), startingTime varchar(255) , duration int);";
        executeQuery(connection, sql);
        System.out.println("Meetings tables created.");

        sql = "DROP TABLE Mapping;";
        executeQuery(connection, sql);

        sql = "CREATE TABLE Mapping (id varchar(255) PRIMARY KEY, meetingID varchar(255), personID varchar(255) );";
        executeQuery(connection, sql);
        System.out.println("Mapping tables created.");

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