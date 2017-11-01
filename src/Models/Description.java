package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Description {
    private int descriptionID;
    private String description;
    private String title;
    private String quantity;
    private String format;

    public Description(int descriptionID, String description, String title, String quantity, String format) {
        this.descriptionID = descriptionID;
        this.description = description;
        this.title = title;
        this.quantity = quantity;
        this.format = format;
    }

    public int getDescriptionID() {
        return descriptionID;
    }

    public void setDescriptionID(int descriptionID) {
        this.descriptionID = descriptionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Description{" +
                "descriptionID=" + descriptionID +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", quantity='" + quantity + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
