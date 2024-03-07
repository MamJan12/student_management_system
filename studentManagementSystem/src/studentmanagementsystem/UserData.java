package studentmanagementsystem;

public class UserData {
    protected Integer id;

    protected Integer dept_id;

    protected String firstName;
    protected String lastName;
    protected String dob;
    protected String matricule;

    protected String gender;

    // Constructor
    public UserData(Integer id, String firstName, String lastName, String dob, String matricule, String gender, Integer dept_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.matricule = matricule;
        this.gender = gender;
        this.dept_id = dept_id;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getDob() {
        return dob;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDept_id() {
        return dept_id;
    }

    // Method to display user data
    public String displayUserData() {
        return gender;
    }
}