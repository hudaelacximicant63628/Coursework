package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class User {
    private int userID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastname;
    private final ObjectProperty<Date> DOB;

    public User(int userID, String firstName, String lastname, Date DOB) {
        this.userID = userID;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastname = new SimpleStringProperty(lastname);
        this.DOB = new SimpleObjectProperty<>(DOB);
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public Date getDOB() {
        return DOB.get();
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setDOB(Date DOB) {
        this.DOB.set(DOB);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}

