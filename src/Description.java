public class Description {
    private int descriptionID;
    private String description;
    private String title;
    private int quantity;
    private String format;

    public Description(int descriptionID, String description, String title, int quantity, String format) {
        this.descriptionID = descriptionID;
        this.description = description;
        this.title = title;
        this.quantity = quantity;
        this.format = format;
    }

    public int getDescriptionID() {
        return descriptionID;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFormat() {
        return format;
    }
}
