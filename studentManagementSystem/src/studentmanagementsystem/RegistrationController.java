package studentmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static studentmanagementsystem.MatriculeGenerator.generateMatricule;

public class RegistrationController {
    @FXML
    private Button registerBtn;

    @FXML
    private TextField fname;

    @FXML
    private TextField password;

    @FXML
    private TextField lname;

    @FXML
    private ChoiceBox<Gender> gender;
    @FXML
    private DatePicker dob;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private PasswordField cpassword;

    @FXML
    private ChoiceBox<String> dpt1;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private int DEPARTMENT_ID;

    private double x= 0 ;
    private double y= 0;

    @FXML
    void close(ActionEvent event) {

    }

    public void initialize() {


        try {
            // Establish database connection
            Connection connection = database.connectDb();

            // Query to retrieve departments from the database
            String query = "SELECT id, name FROM departments";

            // Prepare and execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate department names into an ObservableList
            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                departmentNames.add(name);
            }

            // Populate ChoiceBox with department names
            dpt1.setItems(departmentNames);

            // Handle selection event
            dpt1.setOnAction(event -> {
                // Get the selected department name
                String selectedDepartmentName = dpt1.getSelectionModel().getSelectedItem();

                // Query the database to get the corresponding department ID
                try {
                    PreparedStatement idStatement = connection.prepareStatement("SELECT id FROM departments WHERE name = ?");
                    idStatement.setString(1, selectedDepartmentName);
                    ResultSet idResultSet = idStatement.executeQuery();
                    if (idResultSet.next()) {
                        int id = idResultSet.getInt("id");
                        System.out.println("Selected Department ID: " + id);
                        DEPARTMENT_ID =id;
                    }
                    idResultSet.close();
                    idStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }



        ObservableList<Gender> genders = FXCollections.observableArrayList(
                new Gender("Male"),
                new Gender("Female")
        );

        gender.setItems(genders);

        gender.getSelectionModel().selectFirst();



    }


    public void close() {
        System.exit(0);
    }

    public void register(ActionEvent actionEvent) {
        String sql = "INSERT INTO students (first_name, last_name, password, dob, gender, matricule, dept_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

        connect = database.connectDb();

        try {
            Alert alert;

            String matricule = generateMatricule();

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, fname.getText());
            prepare.setString(2, lname.getText());
            prepare.setString(3, password.getText());
            prepare.setString(4, String.valueOf(dob.getValue()));
            prepare.setString(5, String.valueOf(gender.getValue()));
            prepare.setString(6, matricule);
            prepare.setInt(7, DEPARTMENT_ID);

//            result = prepare.executeUpdate();
            int result = prepare.executeUpdate();
//            CHECK IF FIELDS ARE EMPTTY
            if(fname.getText().isEmpty() || password.getText().isEmpty() || lname.getText().isEmpty()
               || cpassword.getText().isEmpty() || gender.getValue() == null || dob.getValue() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result > 0){

                    // Display an information alert with a TextArea
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Registration Successful");
                    alert2.setHeaderText(null);

                    // Use a TextArea to allow text selection and copying
                    TextArea textArea = new TextArea("Registration Successful! Please login to your account with your matriculation number provided: " + matricule);
                    textArea.setEditable(false); // Make it non-editable
                    textArea.setWrapText(true); // Enable text wrapping
                    textArea.setMaxWidth(Double.MAX_VALUE); // Allow it to expand horizontally

                    alert2.getDialogPane().setContent(textArea);

                    // Show the alert and wait for user interaction
                    alert2.showAndWait();

                    registerBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("login_student.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

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

                }else{
                    // THEN ERROR MESSAGE WILL APPEAR
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred! Please try again later");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
}
