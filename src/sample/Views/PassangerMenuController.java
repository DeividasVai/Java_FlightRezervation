package sample.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Components.DataSets.Flight;
import sample.Components.MainControlPanel.KetvirtasLabor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PassangerMenuController {

    @FXML
    TextField PassName;

    @FXML
    TextField PassSurname;

    @FXML
    TextField PassNationality;

    @FXML
    TextField PassNationalId;

    @FXML
    AnchorPane PassangerInformationPane;

    @FXML
    TableView<Flight> FlightsTable;

    @FXML
    AnchorPane MainPassangerPane;
    @FXML
    AnchorPane fullPVPane;

    @FXML
    Button ShowPassInfo;

    @FXML
    Button BookFlightBtn;

    @FXML
    Button UpdatePassInfo;
    @FXML
    TableColumn<Flight, String> name_col, from_col, to_col, time_col;
    @FXML
    TableColumn<Flight, Integer> number_col, aSeats_col;
    @FXML
    TableColumn<Flight, Double> price_col;

    @FXML
    ComboBox<String> FilterFrom;

    @FXML
    Button goBackToMain;
    KetvirtasLabor Kl;
    Connection connection;

    //ObservableList<Flight> flightList;

    public void initStatus(KetvirtasLabor kl, Connection conn) throws IOException, SQLException {
        connection = conn;
        Kl = kl;
        getComboBoxReady();
        setTable("All");
    }

    public void getComboBoxReady()
    {
        ObservableList<String> uniqueFrom = FXCollections.observableArrayList();
        uniqueFrom.add("All");
        Kl.Manager.Flights.forEach(flight -> {
            if(!uniqueFrom.contains(flight.getFlightFrom().trim()))
                uniqueFrom.add(flight.getFlightFrom().trim());
        });
        FilterFrom.setItems(uniqueFrom);
        FilterFrom.getSelectionModel().selectFirst();
    }

    public void onFilterFromSelectionChanged()
    {
        setTable(FilterFrom.getValue());
    }

    public void setTable(String from)
    {
        FlightsTable.setEditable(false);

        name_col = new TableColumn<>("Name");
        name_col.setCellValueFactory(new PropertyValueFactory<>("FlightName"));
        from_col = new TableColumn<>("From");
        from_col.setCellValueFactory(new PropertyValueFactory<>("FlightFrom"));
        to_col = new TableColumn<>("To");
        to_col.setCellValueFactory(new PropertyValueFactory<>("FlightTo"));
        time_col = new TableColumn<>("Time");
        time_col.setCellValueFactory(new PropertyValueFactory<>("FlightTime"));
        number_col = new TableColumn<>("Number");
        number_col.setCellValueFactory(new PropertyValueFactory<>("FlightNumber"));
        aSeats_col = new TableColumn<>("Seats left");
        aSeats_col.setCellValueFactory(new PropertyValueFactory<>("AvailableSeats"));
        price_col = new TableColumn<>("Price");
        price_col.setCellValueFactory(new PropertyValueFactory<>("Price"));

        ObservableList<Flight> flightList = FXCollections.observableArrayList();

        Kl.Manager.Flights.forEach(flight -> {
            if(flight.getFlightFrom() == from)
                flightList.add(flight);
        });

        if(flightList.size() == 0)
            flightList.addAll(Kl.Manager.Flights);

        FlightsTable.setItems(flightList);
        FlightsTable.getColumns().setAll(name_col, from_col, to_col, time_col, number_col, aSeats_col, price_col);
    }

    int index = 0, currIndex = 0;
    public void BookFlight() throws IOException, SQLException {
        if(Kl.Pass.getName() == null || Kl.Pass.getName() == "")
        {
            alertMessage();
            SetPassInfoVisibility();
            return;
        }
        if(Kl.Pass.getSurname() == null || Kl.Pass.getSurname().length() < 1)
        {
            alertMessage();
            SetPassInfoVisibility();
            return;
        }
        if(Kl.Pass.getNationality() == null || Kl.Pass.getNationality().length() < 1)
        {
            alertMessage();
            SetPassInfoVisibility();
            return;
        }
        if(Kl.Pass.getNationalID() == 0)
        {
            alertMessage();
            SetPassInfoVisibility();
            return;
        }
        else {
            // select other stuff
            if (FlightsTable.getSelectionModel().getSelectedItem() != null)
            {
                index = 0;
                currIndex = 0;
                Kl.Manager.Flights.forEach(flight -> {
                    if (flight.getFlightNumber() == FlightsTable.getSelectionModel().getSelectedItem().getFlightNumber()) {
                        currIndex = index;
                    } else {
                        index++;
                    }
                });

                Statement stmt = null;
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select [PassangerNationalId], [FlightId] from FlightPassangers");
                boolean alreadyBooked = false;
                while(rs.next())
                {
                    if(rs.getString(1).equals(PassNationalId.getText()) && rs.getInt(2) == Kl.Manager.Flights.get(currIndex).getFlightNumber())
                        alreadyBooked = true;
                }

                if(!alreadyBooked)
                {
                    stmt.executeUpdate("insert into FlightPassangers values ('" + PassName.getText() + "','" + PassSurname.getText() + "','" + PassNationality.getText() + "'," + PassNationalId.getText() + "," + Kl.Manager.Flights.get(index).getFlightNumber() + ")");
                    Kl.Manager.BookFlight(Kl.Pass, FlightsTable.getSelectionModel().getSelectedItem());
                }
                else
                    alertMessage_AlreadyBooked();

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nothing to book");
                alert.setHeaderText(null);
                alert.setContentText("No flight has been selected!");

                alert.showAndWait();
            }
        }
    }

    private void alertMessage_AlreadyBooked()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("Already booked!");
        alert.setHeaderText(null);
        alert.setContentText("You have already booked this flight!");

        alert.showAndWait();

        alert = null;
    }

    private void alertMessage()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("Need more information");
        alert.setHeaderText(null);
        alert.setContentText("We are missing some information about you!\nBe sure to have filled all of the fields about yourself");

        alert.showAndWait();

        alert = null;
    }

    public void SetPassInfoVisibility()
    {
        if(PassangerInformationPane.isVisible())
        {
            MainPassangerPane.setVisible(true);
            PassangerInformationPane.setVisible(false);
            ShowPassInfo.setText("Show Passanger Information");
        }
        else
        {
            MainPassangerPane.setVisible(false);
            PassangerInformationPane.setVisible(true);
            ShowPassInfo.setText("Hide Passanger Information");
        }
    }

    public void UpdatePassInformation()
    {
        try{

            Kl.FillPassangerInformation(Integer.parseInt(PassNationalId.getText()), PassNationality.getText(), PassName.getText(), PassSurname.getText());
            PassNationalId.setStyle("-fx-background-color: white;");

        }catch (Exception e){
            if(!PassNationalId.getText().matches("[0-9]+"))
                PassNationalId.setStyle("-fx-background-color: red;");
        }
    }

    public void exitWhindow(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StartupMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Controller controller = loader.getController();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.initStatus(Kl, connection);
    }
}
