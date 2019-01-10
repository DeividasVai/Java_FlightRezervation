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
import sample.Components.DataSets.Passanger;
import sample.Components.DataSets.SqlConnector;
import sample.Components.MainControlPanel.KetvirtasLabor;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControlMenuController {

    @FXML
    Button goBackToMain, createFlightBtn, updateFlightBtn, removeFlightBtn, passangersOnFlightBtn;

    @FXML
    AnchorPane viewFlightsPane, createFlightPane, updateFlightPane, viewPassangersPane;

    // Create flight pane controls
    @FXML
    TextField airportNameTxt, airportLocationTxt, airportIdTxt, flightNameTxt, flightFromTxt, flightToTxt, flightTimeTxt, flightNoTxt, totalSeatsTxt, priceTxt;
    @FXML
    Button cancelCreateBtn, confirmBtn;

    // Update flight pane controls
    @FXML
    TextField airportNameTxt1, airportLocationTxt1, airportIdTxt1, flightNameTxt1, flightFromTxt1, flightToTxt1, flightTimeTxt1, flightNoTxt1, totalSeatsTxt1, priceTxt1;
    @FXML
    Button cancelUpdateBtn, confirmBtn1;

    //Passangers on flight pane
    @FXML
    Button backToMainBtn;
    @FXML
    TableColumn<Passanger, String> passName_col, passSurname_col, passNationality_col, passNationalId_col;
    @FXML
    TableView<Passanger> PassangersTable;

    @FXML
    TableColumn<Flight, String> name_col, from_col, to_col, time_col;
    @FXML
    TableColumn<Flight, Integer> number_col, aSeats_col;
    @FXML
    TableColumn<Flight, Double> price_col;
    @FXML
    TableView<Flight> viewFlightsTable;

    List<Passanger> Passangers = new ArrayList<>();
    Connection connection;
    public KetvirtasLabor Kl;
    public void initStatus(KetvirtasLabor kl, Connection conn)
    {
        connection = conn;
        Kl = kl;
        setViewFlightsTable();
    }

    public void returnToView()
    {
        cleanCreateFlight();
        createFlightPane.setVisible(false);
        updateFlightPane.setVisible(false);
        viewPassangersPane.setVisible(false);
        viewFlightsPane.setVisible(true);
    }

    public void open_PassangerPane() throws SQLException {
        if(viewFlightsTable.getSelectionModel().getSelectedItem() != null)
        {
            viewFlightsPane.setVisible(false);
            viewPassangersPane.setVisible(true);

            Statement stmt = connection.createStatement();
            Passangers.removeAll(Passangers);
            ResultSet rs = stmt.executeQuery("select * from FlightPassangers where [FlightId] = "+ viewFlightsTable.getSelectionModel().getSelectedItem().getFlightNumber());
            while(rs.next())
            {
                Passangers.add(new Passanger(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }

            passName_col = new TableColumn<>("Name");
            passName_col.setCellValueFactory(new PropertyValueFactory<>("Name"));
            passSurname_col = new TableColumn<>("Surname");
            passSurname_col.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            passNationality_col = new TableColumn<>("Nationality");
            passNationality_col.setCellValueFactory(new PropertyValueFactory<>("Nationality"));
            passNationalId_col = new TableColumn<>("National ID");
            passNationalId_col.setCellValueFactory(new PropertyValueFactory<>("NationalID"));


            ObservableList<Passanger> pass = FXCollections.observableArrayList();
            pass.addAll(Passangers);
            PassangersTable.setItems(pass);
            PassangersTable.getColumns().setAll(passName_col, passSurname_col, passNationality_col, passNationalId_col);
        }
    }

    // create flight controls
    public void openCreateFlight()
    {
        viewFlightsPane.setVisible(false);
        createFlightPane.setVisible(true);
    }
    int index, currIndex;
    public void createFlight() {
        try{
            index = 0;
            currIndex = 0;
            Kl.Manager.Flights.forEach(flight -> {
                if(flight.getFlightNumber() == Integer.parseInt(flightNoTxt.getText()))
                {
                    alert_alreadyExists();
                    return;
                }
            });

            Kl.Manager.Flights.add(new Flight(airportNameTxt.getText(), airportLocationTxt.getText(), Integer.parseInt(airportIdTxt.getText()),
                    flightNameTxt.getText(), flightFromTxt.getText(), flightToTxt.getText(), flightTimeTxt.getText(),
                    Integer.parseInt(flightNoTxt.getText()), Integer.parseInt(totalSeatsTxt.getText()),
                    Integer.parseInt(totalSeatsTxt.getText()), Double.parseDouble(priceTxt.getText())));

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("insert into [dbo].[Flights] " +
                    "values (" +
                    "'"+flightNameTxt.getText()+
                    "','"+flightFromTxt.getText()+
                    "','"+flightToTxt.getText()+
                    "','"+flightTimeTxt.getText()+
                    "',"+flightNoTxt.getText()+
                    ","+totalSeatsTxt.getText()+
                    ","+priceTxt.getText()+
                    ",'"+airportNameTxt.getText()+
                    "','"+airportLocationTxt.getText()+
                    "',"+airportIdTxt.getText()+
                    ","+totalSeatsTxt.getText()+")");
            Kl.Manager.populateList();

            setViewFlightsTable();
            returnToView();
        }catch (Exception e){
            e.printStackTrace();
            createFlight_alertMessage();
        }
    }
    private void alert_alreadyExists()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("ERROR!");
        alert.setHeaderText(null);
        alert.setContentText("Flight with this flight number already exists!");
        alert.showAndWait();
    }

    public void cleanCreateFlight()
    {
        airportNameTxt.setText("");
        airportLocationTxt.setText("");
        airportIdTxt.setText("");
        flightNameTxt.setText("");
        flightFromTxt.setText("");
        flightToTxt.setText("");
        flightTimeTxt.setText("");
        flightNoTxt.setText("");
        totalSeatsTxt.setText("");
        priceTxt.setText("");
    }
    private void createFlight_alertMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setTitle("ERROR!");
        alert.setHeaderText(null);
        alert.setContentText("We were unable to create a new flight.\nMake sure all information is correct!");

        alert.showAndWait();

        alert = null;
    }
    // create flight controls

    // update flight controls
    private int updateableIndex;
    private int finalIndex = 0;
    public void openUpdateFlight()
    {
        if(viewFlightsTable.getSelectionModel().getSelectedItem() == null)
        {
            updateFlight_alertMessage(1);
            return;
        }
        viewFlightsPane.setVisible(false);
        updateFlightPane.setVisible(true);

        updateableIndex = 0;
        Kl.Manager.Flights.forEach(flight -> {
            if(flight == viewFlightsTable.getSelectionModel().getSelectedItem())
                finalIndex = updateableIndex;
            else
                updateableIndex++;
        });

        Flight flight = viewFlightsTable.getSelectionModel().getSelectedItem();
        airportNameTxt1.setText(flight.GetAirportName());
        airportLocationTxt1.setText(flight.GetAirportCity());
        airportIdTxt1.setText(Integer.toString(flight.GetAirportID()));

        flightNameTxt1.setText(flight.getFlightName());
        flightFromTxt1.setText(flight.getFlightFrom());
        flightToTxt1.setText(flight.getFlightTo());
        flightTimeTxt1.setText(flight.getFlightTime());
        flightNoTxt1.setText(Integer.toString(flight.getFlightNumber()));
        totalSeatsTxt1.setText(Integer.toString(flight.getTotalSeats()));
        priceTxt1.setText(Double.toString(flight.getPrice()));
    }
    private int atIndex = 0;
    public void onConfirmUpdate() throws SQLException {
        atIndex = 0;
        Kl.Manager.Flights.forEach(flight -> {
            if(atIndex == finalIndex)
            {
                flight.SetAirportName(airportNameTxt1.getText());
                flight.SetAirportCity(airportLocationTxt1.getText());
                flight.SetAirportID(Integer.parseInt(airportIdTxt1.getText()));

                flight.setFlightName(flightNameTxt1.getText());
                flight.setFlightFrom(flightFromTxt1.getText());
                flight.setFlightTo(flightToTxt1.getText());
                flight.setFlightTime(flightTimeTxt1.getText());
                flight.setFlightNumber(Integer.parseInt(flightNoTxt1.getText()));
                flight.setAvailableSeats(flight.getAvailableSeats() + (Integer.parseInt(totalSeatsTxt1.getText()) - flight.getTotalSeats()));
                flight.setTotalSeats(Integer.parseInt(totalSeatsTxt1.getText()));
                flight.setPrice(Double.parseDouble(priceTxt1.getText()));

                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    stmt.executeUpdate("update Flights\n" +
                            "set [AirportName] = '"+ airportNameTxt1.getText() + "', " +
                            "[AirportLocation] = '" + airportLocationTxt1.getText() + "', " +
                            "[AirportId] = "+ airportIdTxt1.getText() + ", " +
                            "[FlightName] = '"+ flightNameTxt1.getText() + "', " +
                            "[FlightFrom] = '"+ flightFromTxt1.getText() +"', " +
                            "[FlightTo] = '"+ flightToTxt1.getText() +"', " +
                            "[FlightTime] = '"+ flightTimeTxt1.getText() +"', " +
                            "[AvailableSeats] = "+ (flight.getAvailableSeats() + (Integer.parseInt(totalSeatsTxt1.getText()) - flight.getTotalSeats())) +", " +
                            "[TotalSeats] = "+ totalSeatsTxt1.getText() +", " +
                            "[Price] = "+ priceTxt1.getText() +
                            "\nwhere [FlightNumber] = "+ flightNoTxt1.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                atIndex++;
            }
        });

        Kl.Manager.populateList();
        setViewFlightsTable();
        returnToView();
    }
    private void updateFlight_alertMessage(int errorId)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(errorId == 1)
        {
            alert.initStyle(StageStyle.UNIFIED);
            alert.setTitle("ERROR!");
            alert.setHeaderText(null);
            alert.setContentText("No flight has been selected in order to update");
            alert.showAndWait();
        }

        alert = null;
    }
    // update flight controls

    private int removeIndex = 0;
    private int finalRemIndex = 0;
    public void openRemoveFlight() throws SQLException {
        removeIndex = 0;
        finalRemIndex = 0;
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Alert window");
        dialog.setHeaderText("If you are sure to remove the item, enter 'Confirm'");
        dialog.setContentText("Enter here:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(result.get().equals("Confirm"))
            {
                Kl.Manager.Flights.forEach(flight -> {
                    if(flight.getFlightNumber() == viewFlightsTable.getSelectionModel().getSelectedItem().getFlightNumber())
                    {
                        finalRemIndex = removeIndex;
                    }
                    removeIndex++;
                });
                Statement stmt = null;
                stmt = connection.createStatement();
                stmt.executeUpdate("delete from Flights where [FlightNumber] = "+ Kl.Manager.Flights.get(finalRemIndex).getFlightNumber());
                Kl.Manager.Flights.remove(finalRemIndex);
                setViewFlightsTable();
            }
        }
    }


    public void setViewFlightsTable()
    {
        viewFlightsTable.setEditable(false);

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
        Kl.Manager.Flights.forEach(flight -> flightList.add(flight));
        viewFlightsTable.setItems(flightList);
        viewFlightsTable.getColumns().setAll(name_col, from_col, to_col, time_col, number_col, aSeats_col, price_col);
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
