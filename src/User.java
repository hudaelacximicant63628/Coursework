public class User {
    private String userID;
    private String firstName;
    private String lastname;
    private String DOB;

    public User(String userID, String firstName, String lastname, String DOB) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastname = lastname;
        this.DOB = DOB;
    }

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDOB() {
        return DOB;
    }
}
