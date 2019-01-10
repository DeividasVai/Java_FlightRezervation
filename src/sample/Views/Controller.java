package sample.Views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sample.Components.DataSets.*;
import sample.Components.MainControlPanel.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    public Connection connection;

    @FXML
    Button openControl;

    @FXML
    Button openPassanger;

    public KetvirtasLabor Kl;
    public void initStatus(KetvirtasLabor kl, Connection conn) throws IOException, SQLException {
        connection = conn;
        Kl = kl;
        if(Kl.Manager == null || Kl.Pass == null)
            Kl.RunProgram(connection);
    }

    @FXML
    public void goToControlView(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ControlMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        ControlMenuController controller = loader.getController();
        controller.initStatus(Kl, connection);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void goToPassangerView(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PassangerMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        PassangerMenuController controller = loader.getController();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.initStatus(Kl, connection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
