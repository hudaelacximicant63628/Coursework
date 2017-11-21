package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;

public class UserView {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastname;
    private final ObjectProperty<LocalDate> DOB;

    public UserView(String firstName, String lastname, LocalDate DOB) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastname = new SimpleStringProperty(lastname);
        this.DOB = new SimpleObjectProperty<LocalDate>(DOB);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public LocalDate getDOB() {
        return DOB.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setDOB(LocalDate DOB) {
        this.DOB.set(DOB);
    }

    @Override
    public String toString() {
        return "UserView{" +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}

