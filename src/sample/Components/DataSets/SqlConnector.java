package sample.Components.DataSets;

import java.sql.*;
import java.util.Properties;

public class SqlConnector {

    static final String DB_URL = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;databaseName=Kursinis;integratedSecurity=true";
    //"jdbc:sqlserver://DESKTOP-RP7HOSQ\SQLEXPRESS;databaseName=Kursinis;integratedSecurity=true"

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}