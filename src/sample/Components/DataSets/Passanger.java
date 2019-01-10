package sample.Components.DataSets;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Passanger 
{


    public int getNationalID() {
        return NationalID.get();
    }

    public SimpleIntegerProperty nationalIDProperty() {
        return NationalID;
    }

    public void setNationalID(int nationalID) {
        this.NationalID.set(nationalID);
    }



    public String getNationality() {
        return Nationality.get();
    }

    public SimpleStringProperty nationalityProperty() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        this.Nationality.set(nationality);
    }



    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }



    public String getSurname() {
        return Surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return Surname;
    }

    public void setSurname(String surname) {
        this.Surname.set(surname);
    }



    public double getMoney() {
        return Money.get();
    }

    public SimpleDoubleProperty moneyProperty() {
        return Money;
    }

    public void setMoney(double money) {
        this.Money.set(money);
    }

    public SimpleIntegerProperty NationalID;
    public SimpleStringProperty Nationality;
    public SimpleStringProperty Name;
    public SimpleStringProperty Surname;
    public SimpleDoubleProperty Money;

    public Passanger()
    {
        Money = new SimpleDoubleProperty(32522.22);
        NationalID = new SimpleIntegerProperty(0);
        Nationality = new SimpleStringProperty();
        Name = new SimpleStringProperty();
        Surname = new SimpleStringProperty();
    }

    public Passanger(String name, String surname, String nat, int natID)
    {
        Name = new SimpleStringProperty(name);
        Surname = new SimpleStringProperty(surname);
        Nationality = new SimpleStringProperty(nat);
        NationalID = new SimpleIntegerProperty(natID);
    }

    
}
