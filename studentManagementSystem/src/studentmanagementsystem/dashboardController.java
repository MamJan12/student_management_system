
package studentmanagementsystem;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static studentmanagementsystem.MatriculeGenerator.generateMatricule;


public class dashboardController implements Initializable {

    @FXML
    private ChoiceBox<String> course_dept;
    @FXML
    private TableColumn<Department, String> dept_name_col;
    @FXML
    private TableView<Department> availableDept_tableView;
    @FXML
    private TableColumn<Department, String> dept_id_col;
    @FXML
    private AnchorPane availableDept_form;

    @FXML
    private Button availableDept_addBtn;
    @FXML
    private TextField name;
    @FXML
    private Button availableDept_updateBtn;
    @FXML
    private Button availableDept_clearBtn;
    @FXML
    private Button availableDept_deleteBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addStudents_btn;

    @FXML
    private Button availableCourse_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private Label home_totalFemale;

    @FXML
    private Label home_totalMale;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private TableView<StudentData2> addStudents_tableView;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_studentNum;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_matricule;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_firstName;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_lastName;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_gender;

    @FXML
    private TableColumn<StudentData2, String> addStudents_col_birth;

    @FXML
    private TextField addStudents_firstName;

    @FXML
    private TextField addStudents_lastName;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private ComboBox<String> addStudents_gender;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private Button addStudents_updateBtn;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private AnchorPane availableCourse_form;

    @FXML
    private TextField code;

    @FXML
    private TextField title;

    @FXML
    private TableView<courseData> availableCourse_tableView;

    @FXML
    private TableColumn<courseData, String> id_col;

    @FXML
    private TableColumn<courseData, String> code_col;

    @FXML
    private TableColumn<courseData, String> title_col;

    @FXML
    private AnchorPane studentGrade_form;

    @FXML
    private TextField studentGrade_studentNum;

    @FXML
    private Label studentGrade_year;

    @FXML
    private Label studentGrade_course;

    @FXML
    private TextField studentGrade_firstSem;

    @FXML
    private TextField studentGrade_secondSem;

    @FXML
    private Button availableDept_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    String MATRICULE;
    int STUDENT_ID;

    public void homeDisplayTotalEnrolledStudents() {

        String sql = "SELECT COUNT(id) FROM students";

        connect = database.connectDb();

        int countEnrolled = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countEnrolled = result.getInt("COUNT(id)");
            }

            home_totalEnrolled.setText(String.valueOf(countEnrolled));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFemaleEnrolled() {

        String sql = "SELECT COUNT(id) FROM students WHERE gender = 'Female'";

        connect = database.connectDb();

        try {
            int countFemale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countFemale = result.getInt("COUNT(id)");
            }

            home_totalFemale.setText(String.valueOf(countFemale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayMaleEnrolled() {

        String sql = "SELECT COUNT(id) FROM students WHERE gender = 'Male'";

        connect = database.connectDb();

        try {
            int countMale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countMale = result.getInt("COUNT(id)");
            }
            home_totalMale.setText(String.valueOf(countMale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addStudentsAdd() {

        String sql = "INSERT INTO students (first_name, last_name, password, dob, gender, matricule) VALUES(?, ?, ?, ?, ?, ?)";

        connect = database.connectDb();

        String matricule = generateMatricule();

        try {
            Alert alert;

            if (addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null ) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }

            else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addStudents_firstName.getText());
                prepare.setString(2, addStudents_lastName.getText());
                prepare.setString(3, matricule);
                prepare.setString(4, String.valueOf(addStudents_birth.getValue()));
                prepare.setString(5, (String) addStudents_gender.getSelectionModel().getSelectedItem());
                prepare.setString(6, matricule);

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                // TO UPDATE THE TABLEVIEW
                addStudentsShowListData();
                // TO CLEAR THE FIELDS
                addStudentsClear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDepartment() {

        String sql = "INSERT INTO departments (name) VALUES(?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (name.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the department name");
                alert.showAndWait();
            }

            else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, name.getText());

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Department Successfully Added!");
                alert.showAndWait();

                // TO UPDATE THE TABLEVIEW
                addStudentsShowListData();
                // TO CLEAR THE FIELDS
                addStudentsClear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudentsShowListData() {
        ObservableList<StudentData2> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM students";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {



            while (result.next()) {


                int id = result.getInt("id");
                String matricule = result.getString("matricule");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String gender = result.getString("gender");
                String dob = result.getString("dob");
                int dept_id = result.getInt("dept_id");

                listData.add(new StudentData2(id, matricule, firstName, lastName, gender, dob, dept_id));

                StudentData2 student = new StudentData2(id, matricule, firstName, lastName, gender, dob, dept_id);

                System.out.println(student.displayUserData());
            }

              addStudents_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("id"));
              addStudents_col_matricule.setCellValueFactory(new PropertyValueFactory<>("firstName"));
              addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
              addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("dob"));
              addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("matricule"));
              addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("gender"));
            addStudents_tableView.setItems(listData);

            addStudents_tableView.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getAllDepartments() {
        ObservableList<Department> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM departments";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {



            while (result.next()) {


                int id = result.getInt("id");
                String name = result.getString("name");

                listData.add(new Department(id, name));

                Department student = new Department(id, name);
            }

            dept_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            dept_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            availableDept_tableView.setItems(listData);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudentsUpdate() {

        String updateData = "UPDATE students SET "
                + "first_name = '" + addStudents_firstName.getText() + "', "
                + "last_name = '" + addStudents_lastName.getText() + "', "
                + "gender = '" + addStudents_gender.getSelectionModel().getSelectedItem() + "', "
                + "dob = '" + addStudents_birth.getValue() + "' "
                + "WHERE matricule = '" + MATRICULE + "'";


        connect = database.connectDb();

        try {
            Alert alert;
            if (addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Student " + addStudents_firstName.getText() + " " + addStudents_lastName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    try (PreparedStatement prepare = connect.prepareStatement(updateData)) {
                        int result = prepare.executeUpdate();
                        // Show information alert upon successful update
                        if (result > 0) {
                            Alert alert2 = new Alert(AlertType.INFORMATION);
                            alert2.setTitle("Information Message");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Successfully Updated!");
                            alert2.showAndWait();

                            // Update the TableView to reflect changes
                            addStudentsShowListData();

                            // Clear the input fields
                            addStudentsClear();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle or log the exception as needed
                    }
                }

                else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudentsDelete() {

        String deleteData = "DELETE FROM students WHERE matricule = '"
                + MATRICULE + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a student to delete");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Student with matricule " + MATRICULE + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String checkData = "SELECT student_id FROM reg_courses "
                            + "WHERE student_id = '" + STUDENT_ID + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

                    // IF THE STUDENT NUMBER IS EXIST THEN PROCEED TO DELETE
                    if (result.next()) {
                        String deleteGrade = "DELETE FROM reg_courses WHERE "
                                + "student_id = '" + STUDENT_ID + "'";

                        statement = connect.createStatement();
                        statement.executeUpdate(deleteGrade);


                        statement = connect.createStatement();
                        statement.executeUpdate(deleteData);

                    }


                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addStudentsClear() {
        addStudents_firstName.setText("");
        addStudents_lastName.setText("");
        addStudents_gender.getSelectionModel().clearSelection();
        addStudents_birth.setValue(null);

    }

    public void addStudentsInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            image = new Image(file.toURI().toString(), 120, 149, false, true);
            addStudents_imageView.setImage(image);

            getData.path = file.getAbsolutePath();

        }
    }


    public void availableCourseAdd() {

        String insertData = "INSERT INTO course (code,title, dep_id) VALUES(?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (code.getText().isEmpty() || title.getText().isEmpty() || course_dept.getValue() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT code FROM course WHERE code = '"
                        + code.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Course: " + code.getText() + " was already registered!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, code.getText());
                    prepare.setString(2, title.getText());
                    prepare.setInt(3, DEPARTMENT_ID);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Course Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableCourseUpdate() {

        String updateData = "UPDATE course SET title = '"
                + title.getText() + "' WHERE code = '"
                + code.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (code.getText().isEmpty() || title.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Course: " + code.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableCourseDelete() {

        String deleteData = "DELETE FROM course WHERE code = '"
                + code.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (code.getText().isEmpty()  || title.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
//                ALL GOOD GUYS! NOW LETS PROCEED TO ADD STUDENTS FORM
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Course: " + code.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableCourseClear() {
        code.setText("");
        title.setText("");
    }

    public void availableDeptClear() {
        name.setText("");
    }

    public ObservableList<courseData> availableCourseListData() {

        ObservableList<courseData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM course";

        connect = database.connectDb();

        try {
            courseData courseD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                courseD = new courseData(
                        result.getString("code"),
                        result.getString("id"),
                        result.getString("title"),
                        getDepartmentById());

                listData.add(courseD);

            }

            System.out.println("DEPT: " + getDepartmentById());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<courseData> availableCourseList;

    public void availableCourseShowListData() {
        availableCourseList = availableCourseListData();

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        code_col.setCellValueFactory(new PropertyValueFactory<>("code"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));

        availableCourse_tableView.setItems(availableCourseList);

    }

    private String DEPARTMENT_NAME;

    private String getDepartmentById() {

        String sql = "SELECT name FROM departments WHERE id = '"+ DEPARTMENT_ID +"'";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {


            while (result.next()) {
                String name = result.getString("name");
                DEPARTMENT_NAME = name;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DEPARTMENT_NAME;
    }

    public void availableCourseSelect() {
        courseData courseD = availableCourse_tableView.getSelectionModel().getSelectedItem();
        int num = availableCourse_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        code.setText(courseD.getCode());
        title.setText(courseD.getTitle());

    }

    public void studentGradesUpdate() {
        double finalCheck1 = 0;
        double finalCheck2 = 0;

        String checkData = "SELECT * FROM student_grade WHERE studentNum = '"
                + studentGrade_studentNum.getText() + "'";

        connect = database.connectDb();

        double finalResult = 0;

        try {

            prepare = connect.prepareStatement(checkData);
            result = prepare.executeQuery();

            if (result.next()) {
                finalCheck1 = result.getDouble("first_sem");
                finalCheck2 = result.getDouble("second_sem");
            }

            if (finalCheck1 == 0 || finalCheck2 == 0) {
                finalResult = 0;
            } else { //LIKE (X+Y)/2 AVE WE NEED TO FIND FOR FINALS
                finalResult = (Double.parseDouble(studentGrade_firstSem.getText())
                        + Double.parseDouble(studentGrade_secondSem.getText()) / 2);
            }

            String updateData = "UPDATE student_grade SET "
                    + " year = '" + studentGrade_year.getText()
                    + "', course = '" + studentGrade_course.getText()
                    + "', first_sem = '" + studentGrade_firstSem.getText()
                    + "', second_sem = '" + studentGrade_secondSem.getText()
                    + "', final = '" + finalResult + "' WHERE studentNum = '"
                    + studentGrade_studentNum.getText() + "'";

            Alert alert;

            if (studentGrade_studentNum.getText().isEmpty()
                    || studentGrade_year.getText().isEmpty()
                    || studentGrade_course.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Student #" + studentGrade_studentNum.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                } else {
                    return;
                }

            }// NOT WE ARE CLOSER TO THE ENDING PART  :) LETS PROCEED TO DASHBOARD FORM
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double x = 0;
    private double y = 0;
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

    public void displayUsername(){
        username.setText(getData.username);
    }
    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addStudents_form.setVisible(false);
            availableCourse_form.setVisible(false);
            availableDept_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            availableDept_btn.setStyle("-fx-background-color:transparent");

            homeDisplayTotalEnrolledStudents();
            homeDisplayMaleEnrolled();
            homeDisplayFemaleEnrolled();

        } else if (event.getSource() == addStudents_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(true);
            availableCourse_form.setVisible(false);
            availableDept_form.setVisible(false);

            addStudents_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            availableDept_btn.setStyle("-fx-background-color:transparent");

            addStudentsShowListData();

        } else if (event.getSource() == availableCourse_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(false);
            availableDept_form.setVisible(false);
            availableCourse_form.setVisible(true);

            availableCourse_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            availableDept_btn.setStyle("-fx-background-color:transparent");

            availableCourseShowListData();

        } else if (event.getSource() == availableDept_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(false);
            availableCourse_form.setVisible(false);
            availableDept_form.setVisible(true);

            availableDept_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

            getAllDepartments();

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            course_dept.setItems(departmentNames);
            course_dept.setValue("Select department");

            // Handle selection event
            course_dept.setOnAction(event -> {
                // Get the selected department name
                String selectedDepartmentName = course_dept.getSelectionModel().getSelectedItem();

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


        ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
        addStudents_gender.setItems(genderOptions);

        displayUsername();
        defaultNav();

        homeDisplayTotalEnrolledStudents();
        homeDisplayMaleEnrolled();
        homeDisplayFemaleEnrolled();


    }

    public StudentData2 selectedStudent(MouseEvent mouseEvent) {
        StudentData2 studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return studentD;
        }

        addStudents_addBtn.setDisable(false);
        addStudents_updateBtn.setDisable(false);
        addStudents_deleteBtn.setDisable(false);
        addStudents_clearBtn.setDisable(false);

        System.out.println("SELECTED MODEL: " + studentD);

        addStudents_firstName.setText(studentD.getLastName());
        addStudents_lastName.setText(studentD.getDob());
        addStudents_gender.setValue(studentD.getMatricule());
        addStudents_birth.setValue(LocalDate.parse(studentD.getGender()));

        MATRICULE = studentD.getFirstName();
        STUDENT_ID = studentD.getId();

        return studentD;
    }

    private int DEPARTMENT_ID;

    public Department selectDepartment(MouseEvent mouseEvent) {
        Department DepartmentD = availableDept_tableView.getSelectionModel().getSelectedItem();
        int num = availableDept_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return DepartmentD;
        }

        availableDept_clearBtn.setDisable(false);
        availableDept_deleteBtn.setDisable(false);
        availableDept_updateBtn.setDisable(false);

        name.setText(DepartmentD.getName());

        DEPARTMENT_ID = DepartmentD.getId();

        return DepartmentD;
    }


    public void availableDeptUpdate() {

        String updateData = "UPDATE departments SET name = '"
                + name.getText() + "' WHERE id = '"
                + DEPARTMENT_ID + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (name.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please provide department name");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Department: " + name.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Department Successfully Updated!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    getAllDepartments();
                    // TO CLEAR THE TEXT FIELDS
                    availableDeptClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteDeptById() {

        String deleteData = "DELETE FROM departments WHERE id = '"
                + DEPARTMENT_ID + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (name.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a department to delete");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Student with matricule " + MATRICULE + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String checkData = "SELECT dept_id FROM students "
                            + "WHERE dept_id = '" + DEPARTMENT_ID + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

                    // IF THE STUDENT NUMBER IS EXIST THEN PROCEED TO DELETE
                    if (result.next()) {
                        String deleteGrade = "DELETE FROM students WHERE "
                                + "dept_id = '" + DEPARTMENT_ID + "'";

                        statement = connect.createStatement();
                        statement.executeUpdate(deleteGrade);


                        statement = connect.createStatement();
                        statement.executeUpdate(deleteData);

                    }


                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    getAllDepartments();
                    // TO CLEAR THE FIELDS
                    availableDeptClear();

                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
