package sample.Components.MainControlPanel;

import sample.Components.DataSets.Passanger;
import sample.Components.DataSets.FlightManager;
import sample.Components.DataSets.SqlConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class KetvirtasLabor 
{

    public FlightManager Manager;
    public Passanger Pass;
    
    public void RunProgram(Connection connection) throws IOException, SQLException {
        Manager = new FlightManager(connection);
        Pass = new Passanger();
    }

    public void FillPassangerInformation(int id, String nat, String name, String surname) throws IOException
    {
        Pass.setNationalID(id);
        Pass.setNationality(nat);
        Pass.setName(name);
        Pass.setSurname(surname);
    }

}

// Airline reservations
// Deivis Airlines