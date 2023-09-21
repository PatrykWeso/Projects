package view;

import DBA.DBManager;
import controller.AddController;
import controller.MainController;
import controller.UpdateController;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Staff;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MainView Class Returns a Javafx View with Table and buttons
 */
public class MainView {

    BorderPane mainPane;
    MainController controller;

    TableView<Staff> tableView =  new TableView<>();

    TableColumn<Staff,Integer> idCol = new TableColumn<>("ID");
    TableColumn<Staff,String> nameCol = new TableColumn<>("Name");
    TableColumn<Staff,Double> sallaryCol = new TableColumn<>("Salary");
    TableColumn<Staff, LocalDate> dateCol = new TableColumn<>("Started Date");

    /**
     * Constructor for MainController
     * @param controller
     */
    public MainView(MainController controller) {
        mainPane= new BorderPane();
        this.controller=controller;
        setColumns();
      loadData();
        configureView();
    }

    /**
     * This method load Staffs using MainController and then set in the Table View
     */
    private void loadData() {
        System.out.println(controller.getStaff());
        this.tableView.getItems().addAll(controller.getStaff());
        this.tableView.refresh();
    }

    /**
     * This method maps the TableView with Staff Object
     */
    public void setColumns(){
        idCol.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("id"));
        idCol.setMinWidth(40);
        nameCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("name"));
        nameCol.setMinWidth(200);
        sallaryCol.setCellValueFactory(new PropertyValueFactory<Staff,Double>("salary"));
        sallaryCol.setMinWidth(150);
        dateCol.setCellValueFactory(new PropertyValueFactory<Staff,LocalDate>("startDate"));
        dateCol.setMinWidth(220);
        tableView.getColumns().add(idCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(sallaryCol);
        tableView.getColumns().add(dateCol);

    }

    /**
     * This method is responsible for Main View Creation
     */
    public void configureView(){


        mainPane.setStyle("-fx-background-color:#ffff");

        HBox topPane =  new HBox();
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(20,20,40,20));
        topPane.setNodeOrientation(NodeOrientation.INHERIT);
        Label title =  new Label("Staff Management System");
        title.setStyle("-fx-text-fill:#060890;-fx-font-size:25px;-fx-font-weight:bold;");
        title.setUnderline(true);
        title.setAlignment(Pos.CENTER);
        topPane.getChildren().add(title);

        //bottom Pane
        HBox bottom =  new HBox(30) ;
        Button AddStaff =  new Button("Add Staff");
        AddStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        AddStaff.setOnMouseEntered(e->{
            AddStaff.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        AddStaff.setOnMouseExited(e->{
            AddStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        AddStaff.setMinWidth(150);
        AddStaff.setCursor(Cursor.HAND);
        AddStaff.setOnAction(e->{
            AddController addController = new AddController();
            AddStaffView addStaffView = new AddStaffView(addController);

            Scene scene =  new Scene(addStaffView.getView());
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });

        //delete button
        Button deleteStaff =  new Button("Delete Staff");
        deleteStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        deleteStaff.setOnMouseEntered(e->{
            deleteStaff.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        deleteStaff.setOnMouseExited(e->{
            deleteStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        deleteStaff.setMinWidth(150);
        deleteStaff.setCursor(Cursor.HAND);
        deleteStaff.setOnAction(actionEvent -> {
            Staff staff = tableView.getSelectionModel().getSelectedItem();
            if(staff==null){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalide Selection");
                alert.setHeaderText("Please Select A Staff to delete");
                alert.setContentText("You have to Select a staff by clicking on a single record in Table");
                alert.show();
            }else {
                DBManager.entityManager.getTransaction().begin();
                DBManager.entityManager.remove(staff);
                DBManager.entityManager.getTransaction().commit();
                Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleted ");
                alert.setHeaderText("The Staff is deleted Successfully");
                alert.setContentText("All information about "+staff.getName()+" is deleted!!!");
                alert.show();
                tableView.getItems().clear();
                tableView.getItems().addAll(controller.getStaff());
                tableView.refresh();
            }
        });

        //update Staff
        Button updateStaff =  new Button("Update Staff");
        updateStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        updateStaff.setOnMouseEntered(e->{
            updateStaff.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        updateStaff.setOnMouseExited(e->{
            updateStaff.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        updateStaff.setMinWidth(150);
        updateStaff.setCursor(Cursor.HAND);

        updateStaff.setOnAction(e->{
            Staff staff = tableView.getSelectionModel().getSelectedItem();
            if(staff==null){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalide Selection");
                alert.setHeaderText("Please Select A Staff to Update");
                alert.setContentText("You have to Select a staff by clicking on a single record in Table");
                alert.show();
            }else{
                UpdateController updateController =  new UpdateController(staff);
                UpdateStaffView updateStaffView =  new UpdateStaffView(updateController,staff);

                Scene scene =  new Scene(updateStaffView.getView());
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        });
        //Give Raise
        Button giveRaise =  new Button("Give Raise");
        giveRaise.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        giveRaise.setOnMouseEntered(e->{
            giveRaise.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        giveRaise.setOnMouseExited(e->{
            giveRaise.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        giveRaise.setMinWidth(150);
        giveRaise.setCursor(Cursor.HAND);
        giveRaise.setOnAction(e->{
            Staff staff = tableView.getSelectionModel().getSelectedItem();
            if(staff==null){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalide Selection");
                alert.setHeaderText("Please Select A Staff");
                alert.setContentText("You have to Select a staff by clicking on a single record in Table");
                alert.show();
            }else {
                staff.getRaise();
                UpdateController updateController = new UpdateController(staff);
                updateController.updateStaff();
                tableView.getItems().clear();
                tableView.getItems().addAll(MainController.findAll());
                tableView.refresh();
            }

        });




        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.getChildren().addAll(AddStaff,updateStaff,deleteStaff,giveRaise);


        //Left Pane

        VBox vb  =  new VBox(10);
        vb.setMinWidth(250);

        vb.setAlignment(Pos.TOP_LEFT);
        vb.setStyle("-fx-background-color:#060890");
        TextField search = new TextField();
        search.setPromptText("Search Staff's Name");
        search.setMinHeight(40);
        search.setFocusTraversable(false);
        Button searchBtn =  new Button("Search");
        searchBtn.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:15px;-fx-border-color:#ffff;");
        searchBtn.setOnMouseEntered(e->{
            searchBtn.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:15px;-fx-border-color:#ffff;");
        });
        searchBtn.setOnMouseExited(e->{
            searchBtn.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:15px;-fx-border-color:#ffff;");
        });
        searchBtn.setMinWidth(100);
        searchBtn.setCursor(Cursor.HAND);
        searchBtn.setOnAction(e->{
            Staff staff= null;
            for(Staff s : MainController.findAll()){
                if(s.getName().equalsIgnoreCase(search.getText())){
                    staff = s;
                    break;
                }
            }
            if(staff==null){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Staff Name");
                alert.setHeaderText("There is no Stuff with Such name");
                alert.setContentText("Staff With Name: "+ search.getText()+" could not be found!!!");
                alert.show();
            }else{
                StaffDetailsView staffDetailsView = new StaffDetailsView(staff);
                Scene scene =  new Scene(staffDetailsView.getView());
                Stage stage  =  new Stage();
                stage.setTitle("Staff Details");
                stage.setScene(scene);
                stage.show();
            }
        });

        Button showAll =  new Button("Show All");
        showAll.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#ffff;");
        showAll.setOnMouseEntered(e->{
            showAll.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#ffff;");
        });
        showAll.setOnMouseExited(e->{
            showAll.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#ffff;");
        });
        showAll.setMinWidth(200);
        showAll.setCursor(Cursor.HAND);
        showAll.setOnAction(w->{
            tableView.getItems().clear();
            tableView.getItems().addAll(MainController.findAll());
            tableView.refresh();
        });

        Button moreThan50000 =  new Button("More than €50,000");
        moreThan50000.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#ffff;");
        moreThan50000.setOnMouseEntered(e->{
            moreThan50000.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#ffff;");
        });
        moreThan50000.setOnMouseExited(e->{
            moreThan50000.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#ffff;");
        });
        moreThan50000.setMinWidth(200);
        moreThan50000.setCursor(Cursor.HAND);
        moreThan50000.setOnAction(w->{
            List<Staff> filterList = MainController.findAll().stream().filter(staff ->
                    (staff.getSalary()>50000)).collect(Collectors.toList());
            tableView.getItems().clear();
            tableView.getItems().addAll(filterList);
            tableView.refresh();
        });

        vb.setPadding(new Insets(20,20,20,20));
        vb.getChildren().addAll(search,searchBtn,new Label(""),new Label(""),moreThan50000,new Label(),showAll);

        mainPane.setLeft(vb);
        mainPane.setTop(topPane);
        mainPane.setBottom(bottom);
        tableView.setStyle("-fx-font-size:17px;");
        mainPane.setCenter(tableView);

    }

    /**
     * returns the View
     * @return JavaFx View
     */
    public BorderPane getView(){
        return mainPane;
    }
}
