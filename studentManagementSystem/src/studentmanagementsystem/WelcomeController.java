package studentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class WelcomeController {

    @FXML
    private Button admin_btn;

    @FXML
    private Button reg_btn;

    private double x= 0 ;
    private double y= 0;

    public void student_button(ActionEvent actionEvent) throws IOException {
        admin_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("login_student.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public void admin_button(ActionEvent actionEvent) throws IOException {
        admin_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("login_admin.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

    }

    public void close() {
        System.exit(0);
    }

    public void reg_button(ActionEvent actionEvent) throws IOException {
        reg_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
}
