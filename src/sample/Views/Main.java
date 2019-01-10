package sample.Views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Components.DataSets.SqlConnector;
import sample.Components.MainControlPanel.KetvirtasLabor;

import java.sql.Connection;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    public KetvirtasLabor kl;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StartupMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Controller controller = loader.getController();
        SqlConnector connection = new SqlConnector();
        Connection con = connection.openConnection();
        controller.initStatus(new KetvirtasLabor(), con);
        primaryStage.setTitle("Deividas Airlines");

        primaryStage.setScene(scene);
        primaryStage.show();
        kl = new KetvirtasLabor();
        kl.RunProgram(con);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
