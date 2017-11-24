package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;

public class UserView {
    private int id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastname;
    private final ObjectProperty<LocalDate> DOB;

    public UserView(int id, String firstName, String lastname, LocalDate DOB) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastname = new SimpleStringProperty(lastname);
        this.DOB = new SimpleObjectProperty<LocalDate>(DOB);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public LocalDate getDOB() {
        return DOB.get();
    }

    public ObjectProperty<LocalDate> DOBProperty() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB.set(DOB);
    }

    @Override
    public String toString() {
        return "UserView{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastname=" + lastname +
                ", DOB=" + DOB +
                '}';
    }
}

