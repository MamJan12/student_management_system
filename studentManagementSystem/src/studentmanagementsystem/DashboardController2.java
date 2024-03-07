/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DashboardController2 implements Initializable {

    @FXML
    private TableColumn<courseData, String> course_type;
    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button reg_courses_btn;

    @FXML
    private Button availableCourse_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane reg_courses_form;

    @FXML
    private AnchorPane availableCourse_form;

    @FXML
    private Button availableCourse_addBtn;

    @FXML
    private Button delCourse_btn;

    @FXML
    private TableView<courseData> availableCourse_tableView;

    @FXML
    private TableView<courseData> availableRegCourse_tableView;

    @FXML
    private TableColumn<courseData, String> id_col;

    @FXML
    private TableColumn<courseData, String> code_col;

    @FXML
    private TableColumn<courseData, String> title_col;

    @FXML
    private TableColumn<courseData, String> reg_id_col;

    @FXML
    private TableColumn<courseData, String> reg_code_col;

    @FXML
    private TableColumn<courseData, String> reg_title_col;

    @FXML
    private Label dept;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    @FXML
    private Label name_prof;
    @FXML

    private Label matricule_prof;
    @FXML

    private Label dob_prof;

    @FXML
    private Label gender_prof;

    private int DEPARTMENT_ID;

    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void defaultNav(){
        home_btn.setStyle("-fx-background-color: #04043c;");
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableCourse_form.setVisible(false);
            reg_courses_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: #04043c;");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            reg_courses_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == availableCourse_btn) {
            home_form.setVisible(false);
            availableCourse_form.setVisible(true);
            reg_courses_form.setVisible(false);

            availableCourse_btn.setStyle("-fx-background-color: #04043c");
            home_btn.setStyle("-fx-background-color:transparent");
            reg_courses_btn.setStyle("-fx-background-color:transparent");

            courseList();

        } else if (event.getSource() == reg_courses_btn) {
            home_form.setVisible(false);
            availableCourse_form.setVisible(false);
            reg_courses_form.setVisible(true);

            reg_courses_btn.setStyle("-fx-background-color: #04043c");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");

            regCourseList();
        }
    }

    public void close() {
        System.exit(0);
    }

    int studentId;

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void getUser() {
        ObservableList<StudentData2> userData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM students WHERE matricule = '" + getData.username + "' ";

        connect = database.connectDb();

        try {
            StudentData2 studentD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentD = new StudentData2(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("dob"),
                        result.getString("gender"),
                        result.getString("matricule"),
                        result.getInt("dept_id")
                );

                userData.add(studentD);

                name_prof.setText(studentD.getFirstName() + " " + studentD.getLastName());
                dob_prof.setText(String.valueOf(studentD.getDob()));
                matricule_prof.setText(studentD.getGender());
                gender_prof.setText(studentD.getMatricule());
                username.setText(studentD.getFirstName());
                studentId = studentD.getId();

                DEPARTMENT_ID = studentD.getDept_id();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String courseType;

    public void courseList() {

        String listCourse = "SELECT c.* FROM course c LEFT JOIN reg_courses rc ON c.id = rc.course_id AND rc.student_id = '"+ studentId +"' WHERE rc.course_id IS NULL";

        connect = database.connectDb();

        try {

            ObservableList listC = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(listCourse);
            result = prepare.executeQuery();

            while (result.next()) {
                String id = result.getString("id");
                String code = result.getString("code");
                String title = result.getString("title");

                if (DEPARTMENT_ID == result.getInt("dep_id")) {
                    courseType = "MAJOR";
                } else if (code.startsWith("FRE 1") || code.startsWith("ENG 1") || code.startsWith("SPT 1") || code.startsWith("CVE 1")){
                    courseType = "UNIVERSITY REQUIREMENT";
                } else {
                    courseType = "ELECTIVE";
                }
                listC.add(new courseData (id, code, title, courseType));
            }

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            code_col.setCellValueFactory(new PropertyValueFactory<>("code"));
            title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            course_type.setCellValueFactory(new PropertyValueFactory<>("dep_name"));

            availableCourse_tableView.setItems(listC);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public courseData availableCourseSelect() {
        courseData courseD = availableCourse_tableView.getSelectionModel().getSelectedItem();
        int num = availableCourse_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return courseD;
        }

        availableCourse_addBtn.setDisable(false);

        return courseD;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUser();
        defaultNav();
        getDepartmentById();
    }

    public void register(ActionEvent actionEvent) {
        courseData course = availableCourseSelect();

        String insertData = "INSERT INTO reg_courses (student_id, course_id) VALUES(?,?)";

        try {
            Alert alert;

//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!

                String checkData = "SELECT COUNT(*) AS num_registrations FROM reg_courses WHERE student_id = '" + studentId + "' AND course_id = '" + course.getId() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                result.next();
                int numRegistrations = result.getInt("num_registrations");


            if (numRegistrations > 0) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(course.getTitle() + " was already registered!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, studentId);
                    prepare.setString(2, course.getId());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered!");
                    alert.showAndWait();

                    courseList();
                    availableCourse_addBtn.setDisable(true);

                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect = database.connectDb();
    }

    private String DEPARTMENT_NAME;

    private String getDepartmentById() {

        String sql = "SELECT name FROM departments WHERE id = '"+ DEPARTMENT_ID +"'";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {


            while (result.next()) {
                String name = result.getString("name");
                dept.setText(name);

                DEPARTMENT_NAME = name;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DEPARTMENT_NAME;
    }

    public void regCourseList() {

        String listCourse = "SELECT c.* FROM course c JOIN reg_courses rc ON c.id = rc.course_id WHERE rc.student_id = '"+ studentId +"'";

        connect = database.connectDb();

        try {

            ObservableList listC = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(listCourse);
            result = prepare.executeQuery();

            while (result.next()) {
//                listC.add(result.getString("title"));
                String id = result.getString("id");
                String code = result.getString("code");
                String title = result.getString("title");
                listC.add(new courseData (id, code, title, getDepartmentById()));
            }

            reg_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            reg_code_col.setCellValueFactory(new PropertyValueFactory<>("code"));
            reg_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));

            availableRegCourse_tableView.setItems(listC);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public courseData regCourseSelect() {
        courseData courseD = availableRegCourse_tableView.getSelectionModel().getSelectedItem();
        int num = availableRegCourse_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return courseD;
        }

        delCourse_btn.setDisable(false);

        System.out.println("Selected: " + courseD);

        return courseD;
    }

    public void availableCourseDelete() {
        courseData course = regCourseSelect();

        String deleteData = "DELETE FROM reg_courses WHERE course_id = ?";

        connect = database.connectDb();

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Course: " + course.getTitle() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                // Using a prepared statement to avoid SQL injection
                PreparedStatement preparedStatement = connect.prepareStatement(deleteData);
                preparedStatement.setString(1, course.getId());
                preparedStatement.executeUpdate();

                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Information Message");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Successfully Deleted!");
                successAlert.showAndWait();

                regCourseList();

                delCourse_btn.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, for example, show an error message
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error Message");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while deleting the course.");
            errorAlert.showAndWait();

            delCourse_btn.setDisable(true);
        } finally {
            // Close resources if necessary
            delCourse_btn.setDisable(true);
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
