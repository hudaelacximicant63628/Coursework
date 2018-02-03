package Models;

import Controllers.MainControllers;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;


    public class AssignmentsStage extends Application {
        private Button add;
        private Button addUser;
        private Button remove;
        private Button modify;
        private Button userSceneButton;
        private Button AssignmentSceneButton;
        private Button User;
        private Button removeUser;
        private Button notesStage;

        private DatePicker deadline;
        private TextField teacherName;
        private TextField className;
        private TextField description;
        private TextField title;
        private TextField quantity;
        private TextField format;
        public static TextArea errorReporter;
        private static ListView<User> userListView;

        private DatePicker DOB;
        private TextField userName;
        private TextField lastName;

        private MainControllers mainControllers;
        public static AssignmentsView enteredData;
        private static ObservableList<AssignmentsView> tableViewData = FXCollections.observableArrayList();
        public static ObservableList<User> userListViewData = FXCollections.observableArrayList();
        public static User userData;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage assignmentStage) {
            mainControllers = new MainControllers();
            TableView<AssignmentsView> tableView = new TableView();

            add = new Button("Add");
            addUser = new Button("Add user");

            remove = new Button("Remove");
            modify = new Button("Modify");
            userSceneButton = new Button("User");
            AssignmentSceneButton = new Button("Assignments");
            User = new Button("Use");
            removeUser = new Button("Remove");
            notesStage = new Button("Notes");


            DOB = new DatePicker();
            DOB.setPromptText("Enter DOB");
            userName = new TextField();
            userName.setPromptText("Enter first name");
            lastName = new TextField();
            lastName.setPromptText("Enter last name");

            title = new TextField();
            title.setPromptText("Enter title");
            description = new TextField();
            description.setPromptText("Enter description");
            className = new TextField();
            className.setPromptText("Enter class name");
            teacherName = new TextField();
            teacherName.setPromptText("Enter teacher name");
            deadline = new DatePicker();
            deadline.setPromptText("Enter deadline");
            quantity = new TextField();
            quantity.setPromptText("Enter quantity");
            format = new TextField();
            format.setPromptText("Enter format");
            errorReporter = new TextArea();
            errorReporter.setMaxHeight(100);
            errorReporter.setEditable(false);
            errorReporter.setStyle("-fx-font-size: 20");
            userListView = new ListView();
            userListView.setMinWidth(500);

            userListView.setItems(userListViewData);

            HBox userSceneHBox = new HBox();
            HBox userSceneButtonsHBox = new HBox();

            HBox assignmentSceneHBox = new HBox();
            assignmentSceneHBox.getChildren().addAll(add, remove, modify, userSceneButton, notesStage);

            VBox assignmentSceneVBox = new VBox();
            assignmentSceneVBox.getChildren().addAll(tableView, title, description, className, teacherName, deadline, quantity, format, assignmentSceneHBox, errorReporter);
            assignmentSceneVBox.setSpacing(2);

            VBox userSceneVBOX = new VBox();
            userSceneButtonsHBox.getChildren().addAll(AssignmentSceneButton, addUser, User, removeUser);
            userSceneButtonsHBox.setSpacing(10);
            userSceneVBOX.getChildren().addAll(userName,lastName, DOB, userSceneButtonsHBox);
            userSceneHBox.getChildren().addAll(userSceneVBOX, userListView);
            userSceneVBOX.setSpacing(10);
            userSceneHBox.setSpacing(10);

            userSceneButton.setLineSpacing(10);

            assignmentSceneHBox.setPadding(new Insets(10,20,20,20));
            assignmentSceneHBox.setSpacing(10);

            Scene assignmentScene = new Scene(assignmentSceneVBox, 1000, 830);
            Scene userScene = new Scene(userSceneHBox, 800, 200);
            assignmentStage.setTitle("School planner");

            Image icon = new Image(getClass().getResourceAsStream("/Resources/school_planner_icon.png"));
            assignmentStage.getIcons().add(icon);

            //TABLE COLUMNS

            //has equal spacing for each column


            tableView.setPrefSize(400, 300);
            tableView.setItems(tableViewData);

            TableColumn titleColumn = new TableColumn<>("Title");
            titleColumn.setMinWidth(200);
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            tableView.getColumns().add(titleColumn);

            TableColumn classColumn = new TableColumn<>("Class");
            classColumn.setCellValueFactory(new PropertyValueFactory<>("classroom"));
            tableView.getColumns().add(classColumn);

            TableColumn formatColumn = new TableColumn<>("Format");
            formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));
            tableView.getColumns().add(formatColumn);

            TableColumn deadlineColumn = new TableColumn<>("Deadline");
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            tableView.getColumns().add(deadlineColumn);

            TableColumn quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            tableView.getColumns().add(quantityColumn);

            TableColumn descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setMinWidth(500);
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            tableView.getColumns().add(descriptionColumn);

//            can be used to get ID selected from tableview so that data can be easily removed/updated
//            tableView.getSelectionModel().getSelectedItem().getAssignmentID();
//            tableView.getSelectionModel().getSelectedItem().getDescriptionID();
//
            //Button-------------------------------------------------------------------------------------------------------

            add.setOnAction(e -> {
                errorReporter.clear();
                getAssignmentData();
                mainControllers.addAssignment();
                clearTextFields();
            });
            User.setOnAction(e -> {
                userData = userListView.getSelectionModel().getSelectedItem();
                System.out.println(userData);
                mainControllers.updateAssignmentsViewTableView();
            });
            remove.setOnAction(e -> mainControllers.deleteAssignment(tableView.getSelectionModel().getSelectedItem()));
            modify.setOnAction(e -> {
                getAssignmentData();
                mainControllers.modifyAssignment(tableView.getSelectionModel().getSelectedItem());

            });
            userSceneButton.setOnAction(e -> assignmentStage.setScene(userScene));
            AssignmentSceneButton.setOnAction(e -> assignmentStage.setScene(assignmentScene));
            addUser.setOnAction(e ->
            {
                errorReporter.clear();
                getUserInfo();
                mainControllers.addUser();
                clearTextFields();
            });
            removeUser.setOnAction(e -> {
                userData = userListView.getSelectionModel().getSelectedItem();
                mainControllers.deleteUser();
            });
            notesStage.setOnAction(e -> mainControllers.runNotesStage());

            tableView.setMinHeight(470);

            mainControllers.updateUserListView();
            errorReporter.setText("Pick a user and add assignments");
            mainControllers.closeConfirmation(assignmentStage);
            assignmentStage.setScene(assignmentScene);
            assignmentStage.show();

        }

        public void getUserInfo() {
                String firstName = this.userName.getText();
                String lastName = this.lastName.getText();
                LocalDate DOB = this.DOB.getValue();

                LocalDate localDate = LocalDate.now();

                try {
                    if (DOB.isAfter(localDate) || firstName.trim().equals("") || lastName.trim().equals("") || DOB.equals("")) {
                        userData = null;
                    } else {
                        userData = new User(0, firstName, lastName, DOB);
                    }
                }catch (NullPointerException e){
                }
        }

        public void getAssignmentData(){
                String classroom = this.className.getText();
                String description = this.description.getText();
                String title = this.title.getText();
                String teacher = this.teacherName.getText();
                String format = this.format.getText();
                LocalDate deadline = this.deadline.getValue();

                LocalDate dateNow = LocalDate.now();


                try {

                    if (deadline.isBefore(dateNow) || classroom.trim().equals("") || description.trim().equals("") || title.trim().equals("") ||
                            teacher.trim().equals("") || this.quantity.getText().equals("") || format.trim().equals("") || deadline.equals("")) {
                        enteredData = null;
                        AssignmentsStage.errorReporter.setText("Data has not been entered properly");
                    } else {
                        int quantity = Integer.parseInt(this.quantity.getText());
                        //quantity must be greater than 0
                        if(quantity > 0) {
                            //for it to update the enteredData static variable
                            enteredData = new AssignmentsView(teacher, classroom, description, title, quantity, format, deadline);
                        }
                    }
                }catch (Exception e){
                }


        }

        public void clearTextFields(){
            title.clear();
            description.clear();
            className.clear();
            teacherName.clear();
            quantity.clear();
            format.clear();
            userName.clear();
            lastName.clear();
            DOB.getEditor().clear();
            deadline.getEditor().clear();
        }



        public static ObservableList getTableViewData(){
            return tableViewData;
        }

    }
