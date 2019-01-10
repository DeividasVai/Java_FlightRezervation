package sample.Components.DataSets;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FlightManager 
{
    public List<Flight> Flights = new ArrayList<>();
    public Connection connection;
    public double currentTotal = 0;
    
    public FlightManager(Connection conn) throws SQLException, IOException {
        //CreateFlightObjects();
        connection = conn;
        populateList();
    }

    // MAYBE USED LATER
    public void populateList() throws SQLException {
        Flights.removeAll(Flights);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from [dbo].[Flights]");
        while (rs.next()) {
            String airportName = rs.getString(8);
            String airportCity = rs.getString(9);
            int airportId = rs.getInt(10);
            String flightName = rs.getString(1);
            String flightFrom = rs.getString(2);
            String flightTo = rs.getString(3);
            String flightTime = rs.getString(4);
            int flightNumber = rs.getInt(5);
            int totalSeats = rs.getInt(6);
            int availableSeats = rs.getInt(11);
            double price = Double.parseDouble(rs.getBigDecimal(7).toString());

            Flights.add(new Flight(airportName, airportCity, airportId, flightName, flightFrom,
                    flightTo, flightTime, flightNumber, totalSeats, availableSeats, price));

        }

        Flights.forEach(flight -> {
            System.out.println(flight.FlightNumber);
        });
    }

    // MANDATORY FOR NOW!
    public void CreateFlightObjects() throws IOException // Same as Read from file flight
    {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Developer\\Desktop\\FlightInformation\\Flights.txt")); 
  
        String name = "";
        String from = "";
        String to = "";
        String fNo = "";
        String time = "";
        String tSeats = "";
        String aSeats = "";
        String price = "";
        
        String airportName = "";
        String airportCity = "";
        String airportID = "";
        
        String st;
        int line = -1;
        while ((st = br.readLine()) != null) 
        {            
            switch(line)
            {
                case -1:
                    break;
                case 0:
                    name = st;
                    break;
                case 1:
                    from = st;
                    break;
                case 2:
                    to = st;
                    break;
                case 3:
                    time = st;
                    break;
                case 4:
                    fNo = st;
                    break;
                case 5:
                    tSeats = st;
                    break;
                case 6:
                    aSeats = st;
                    break;
                case 7:
                    price = st;
                    break;
                case 8:
                    airportName = st;
                    break;
                case 9:
                    airportCity = st;
                    break;
                case 10:
                    airportID = st;
                    break;
                default:
                    line = -1;
                    Flights.add(new Flight(airportName, airportCity, Integer.parseInt(airportID), name, from, to, time, Integer.parseInt(fNo), Integer.parseInt(tSeats),
                        Integer.parseInt(aSeats), Double.parseDouble(price)));
                    break;
            }     
            line++;
        }
        
        Flights.add(new Flight(airportName, airportCity, Integer.parseInt(airportID), name, from, to, time, Integer.parseInt(fNo), Integer.parseInt(aSeats),
                        Integer.parseInt(tSeats), Double.parseDouble(price)));
        br.close();
    }

    public void CreateNewFlight() throws IOException
    {
        Scanner scan= new Scanner(System.in);
        System.out.println("Add the folowing information:");
        System.out.println("Flight Name:");
        String FlightName = scan.nextLine();
        System.out.println("Flight From:");
        String FlightFrom = scan.nextLine();
        System.out.println("Flight To:");
        String FlightTo = scan.nextLine();
        System.out.println("Flight Time:");
        String FlightTime = scan.nextLine();
        System.out.println("A unique flight number:");
        String FlightNo = scan.nextLine(); 
        System.out.println("Total seats:");
        String TotalSeats = scan.nextLine(); 
        System.out.println("Ticket price:");
        String Price = scan.nextLine(); 
        System.out.println("Airport name:");
        String airportName = scan.nextLine();
        System.out.println("City of airport:");
        String airportCity = scan.nextLine();
        System.out.println("Airport id:");
        String airportID = scan.nextLine();
        Flights.add(new Flight(airportName, airportCity, Integer.parseInt(airportID), FlightName, FlightFrom, FlightTo, FlightTime, Integer.parseInt(FlightNo), 
                Integer.parseInt(TotalSeats), Integer.parseInt(TotalSeats), Double.parseDouble(Price)));
        Flight flight = Flights.get(Flights.size()-1);

    }
    
    public void UpdateFlight(boolean update) throws IOException
    {
        System.out.println("To cancel the selection - type in 'STOP'");
        Scanner scan= new Scanner(System.in);
        if(update)
            System.out.println("Type in the flight number to identify what you want to update:");
        else            
            System.out.println("Type in the flight number to identify what you want to delete:");
        String flightNumber = scan.nextLine();
        
        int flightNo = 0;
        if(flightNumber.length() == 0)
        {
    		return;
        }
                
        if(flightNumber.toUpperCase().equals("STOP"))
        {
    		return;
        }
        
        try{
            flightNo = Integer.parseInt(flightNumber);
        }catch (Exception e){
            System.out.println("The format in which you typed in the flight number was incorrect.");
            System.out.println(e.getMessage());            
            UpdateFlight(update);
        }
        
        int indexOfList = 0;
        for(Flight flight : Flights) 
        { 
            if(flight.getFlightNumber() == flightNo)
            { 
                if(update)
                {
                    ContinueUpdateFlight(flight);

                }
                else
                {
                    DeleteFlight(indexOfList);
                    System.out.println("Flight removed from the list");
                }
                return;
            }
            indexOfList++;
        }
        
        System.out.println("Flight with that number not found.");
    }
    
    public void ContinueUpdateFlight(Flight flight)
    {
        Scanner scan= new Scanner(System.in);        
        System.out.println("To quit the menu, please type in 'STOP'");
        System.out.println("Which segment do you wish to update?");
        
        System.out.println("1  | Flight name: " + flight.getFlightName());
        System.out.println("2  | Flight From: " + flight.getFlightFrom());
        System.out.println("3  | Flight To: " + flight.getFlightTo());
        System.out.println("4  | Flight Time: " + flight.getFlightTime());
        System.out.println("5  | Total seats: " + flight.getTotalSeats());
        System.out.println("6  | Available seats: " + flight.getAvailableSeats());
        System.out.println("7  | Price: " + flight.getPrice());
        System.out.println("8  | Airport name: " + flight.GetAirportName());
        System.out.println("9  | City of airport: " + flight.GetAirportCity());
        System.out.println("10 | Airport id: " + flight.GetAirportID());
        
        String strSelection = scan.nextLine();
        if(strSelection.toUpperCase().equals("STOP"))
        {
    		return;
        }
        
        try{
            int intSelection = Integer.parseInt(strSelection);
            switch(intSelection)
            {
                case 1:
                    System.out.println("Update flight name:");
                    String name = scan.nextLine();
                    flight.setFlightName(name);
                    break;
                case 2:
                    System.out.println("Update flight from:");
                    String from = scan.nextLine();
                    flight.setFlightFrom(from);
                    break;
                case 3:
                    System.out.println("Update flight to:");
                    String to = scan.nextLine();
                    flight.setFlightTo(to);
                    break;
                case 4:
                    System.out.println("Update flight time:");
                    String time = scan.nextLine();
                    flight.setFlightTime(time);
                    break;
                case 5:
                    System.out.println("Update flight total seats:");
                    String tSeats = scan.nextLine();
                    try{
                        flight.setTotalSeats(Integer.parseInt(tSeats));
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Update flight available seats:");
                    String aSeats = scan.nextLine();
                    try{
                        flight.setAvailableSeats(Integer.parseInt(aSeats));
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    System.out.println("Update flight price:");
                    String price = scan.nextLine();
                    try{
                        flight.setPrice(Double.parseDouble(price));
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    System.out.println("Update airport name:");
                    String airportName = scan.nextLine();
                    flight.setFlightTime(airportName);
                    break;
                case 9:
                    System.out.println("Update airport city:");
                    String airportCity = scan.nextLine();
                    flight.setFlightTime(airportCity);
                    break;
                case 10:
                    System.out.println("Update airport city:");
                    String airportID = scan.nextLine();
                    flight.SetAirportID(Integer.parseInt(airportID));
                    break;
                default:
                    System.out.println("Selection not available");
                    break;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        ContinueUpdateFlight(flight);        
    }
    
    public void DeleteFlight(int index) throws IOException
    {
        Scanner scan= new Scanner(System.in);      
        System.out.println("Are you sure you want to delete the flight? Y/N");
        if(scan.nextLine().toUpperCase().equals("Y"))
        {
            Flights.remove(index);
        }        
    }

    public void PrintBookedPassangers()
    {
    	for(Flight flight : Flights)
    	{
    		System.out.println("Flight no. " + flight.getFlightNumber() + ". Total passangers: " + flight.BookedPassangers.size());
    		for(Passanger passanger : flight.BookedPassangers)
    		{
    			System.out.println(passanger.getName() + " " + passanger.getSurname());
    		}
    	}
    }

    public void BookFlight(Passanger passanger, Flight flight)
    {
        if(flight.getAvailableSeats() > 0)
        {
            System.out.println("\nAre you sure you want to continue with the purchase? Y/N");
            String selection = "Y";
            if(selection.toUpperCase().equals("Y"))
            {
                if(passanger.getMoney() >= flight.getPrice())
                {
                    currentTotal = flight.getPrice();

                    flight.setAvailableSeats(flight.getAvailableSeats() - 1);
                    flight.BookedPassangers.add(passanger);
                    System.out.println("Flight booked");
                    return;
                }
                else
                {
                    System.out.println("We are sorry, but you do not have enough money to buy the tickets.");
                }
            }
            else
            {
                return;
            }
        }
        else
        {
            System.out.println("We are sorry, but there are no more available tickets.");
        }
    }
}