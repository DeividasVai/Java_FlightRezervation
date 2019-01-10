package sample.Components.DataSets;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Flight extends Airport
{
    public final SimpleStringProperty FlightName;
    public final SimpleStringProperty FlightFrom;
    public final SimpleStringProperty FlightTo;
    public final SimpleStringProperty FlightTime;
    public final SimpleIntegerProperty FlightNumber;
    public final SimpleIntegerProperty TotalSeats;
    public final SimpleIntegerProperty AvailableSeats;

    public String getFlightName() {
        return FlightName.get();
    }

    public SimpleStringProperty flightNameProperty() {
        return FlightName;
    }

    public void setFlightName(String flightName) {
        this.FlightName.set(flightName);
    }

    public String getFlightFrom() {
        return FlightFrom.get();
    }

    public SimpleStringProperty flightFromProperty() {
        return FlightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        this.FlightFrom.set(flightFrom);
    }

    public String getFlightTo() {
        return FlightTo.get();
    }

    public SimpleStringProperty flightToProperty() {
        return FlightTo;
    }

    public void setFlightTo(String flightTo) {
        this.FlightTo.set(flightTo);
    }

    public String getFlightTime() {
        return FlightTime.get();
    }

    public SimpleStringProperty flightTimeProperty() {
        return FlightTime;
    }

    public void setFlightTime(String flightTime) {
        this.FlightTime.set(flightTime);
    }

    public int getFlightNumber() {
        return FlightNumber.get();
    }

    public SimpleIntegerProperty flightNumberProperty() {
        return FlightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.FlightNumber.set(flightNumber);
    }

    public int getTotalSeats() {
        return TotalSeats.get();
    }

    public SimpleIntegerProperty totalSeatsProperty() {
        return TotalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.TotalSeats.set(totalSeats);
    }

    public int getAvailableSeats() {
        return AvailableSeats.get();
    }

    public SimpleIntegerProperty availableSeatsProperty() {
        return AvailableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.AvailableSeats.set(availableSeats);
    }

    public double getPrice() {
        return Price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price.set(price);
    }

    public final SimpleDoubleProperty Price;
    
    public List<Passanger> BookedPassangers = new ArrayList<>();
    
    public Flight(String airportN, String city, int id, String name, String from, String to, String time, 
                    int number, int tSeats, int aSeats, double price)
    {
    	AirportName = airportN;
    	City = city;
        FlightName= new SimpleStringProperty(name);
        FlightFrom = new SimpleStringProperty(from);
        FlightTo = new SimpleStringProperty(to);
        FlightTime = new SimpleStringProperty(time);
        FlightNumber = new SimpleIntegerProperty(number);
        TotalSeats = new SimpleIntegerProperty(tSeats);
        AvailableSeats = new SimpleIntegerProperty(aSeats);
        Price = new SimpleDoubleProperty(price);
        AirportID = id;
    }


}
