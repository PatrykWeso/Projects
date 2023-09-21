package view;

import DBA.DBManager;
import controller.AddController;
import controller.MainController;
import controller.UpdateController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Administration;
import model.Engineering;
import model.Staff;
import model.StudentInternships;

import java.nio.Buffer;
import java.time.LocalDate;
import java.util.Optional;

/**
 * This Class gives a Staff's Update form View
 */
public class UpdateStaffView {

    BorderPane mainPain;
    UpdateController controller;

    Staff staff;

    /**
     * Constructor for UpdateStaffView
     * @param controller
     * @param staff
     */
    public UpdateStaffView(UpdateController controller, Staff staff) {
        this.controller = controller;
        this.staff = staff;
        configureView();
    }

    /**
     * This method is responsible for Main View Creation
     */
    private void configureView() {
        this.mainPain = new BorderPane();
        this.mainPain.setMinSize(400,350);
        this.mainPain.setPadding(new Insets(30,30,30,30));
        mainPain.setStyle("-fx-background-color:#ffff");

        HBox topPane =  new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title =  new Label("Update Staff");
        title.setStyle("-fx-text-fill:#060890;-fx-font-size:20px;-fx-font-weight:bold;");
        title.setUnderline(true);
        title.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(20,20,20,20));
        topPane.getChildren().add(title);

        //
        HBox centerMainPane = new HBox(30);

        //

        ComboBox<String> comboBox =  new ComboBox<>();
        comboBox.getItems().addAll("Administration","Student Internships","Engineering");
        comboBox.setMinHeight(30);


        VBox centerLeftPane =  new VBox(30);
        centerLeftPane.setMinWidth(150);
        VBox centerRightPane =  new VBox(20);
        Label staffTypeL =  new Label("Staff Type:");
        staffTypeL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label nameL =  new Label("Staff's Name:");
        nameL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label salaryL = new Label("Staff's Salary:");
        salaryL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label startDateL = new Label("Start Date:");
        startDateL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label phoneL =  new Label("Admin's Phone:");
        phoneL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label emailL = new Label("Email:");
        emailL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label uniL = new Label("Univercity Name:");
        uniL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");
        Label mentorL =  new Label("Mentor :");
        mentorL.setStyle("-fx-text-fill:#060890;-fx-font-size:15px;");

        TextField nameTf =  new TextField();
        nameTf.setPromptText("Name");
        nameTf.setMinHeight(30);
        nameTf.setText(staff.getName());

        TextField salaryTf =  new TextField();
        salaryTf.setPromptText("Salary");
        salaryTf.setMinHeight(30);
        salaryTf.setText(String.valueOf(staff.getSalary()));

        DatePicker startDateTf =  new DatePicker();
        startDateTf.setValue(LocalDate.now());
        startDateTf.setMinHeight(30);
        startDateTf.setValue(staff.getStartDate());

        TextField phoneTf =  new TextField();
        phoneTf.setPromptText("Phone Number");
        phoneTf.setMinHeight(30);

        TextField emailTf =  new TextField();
        emailTf.setPromptText("Email");
        emailTf.setMinHeight(30);

        TextField uniTf =  new TextField();
        uniTf.setPromptText("University Name");
        uniTf.setMinHeight(30);

        ComboBox<Staff> mentorTf =  new ComboBox<>();
        mentorTf.getItems().addAll(MainController.findAll());
        mentorTf.setMinHeight(30);

        centerLeftPane.getChildren().addAll(staffTypeL,nameL,salaryL,startDateL,phoneL,emailL,uniL,mentorL);
        centerRightPane.getChildren().addAll(comboBox,nameTf,salaryTf,startDateTf,phoneTf,emailTf,uniTf,mentorTf);


            if(staff instanceof Administration){
                comboBox.getSelectionModel().select("Administration");
                Administration administration = (Administration) staff;
                phoneTf.setText(String.valueOf(administration.getPhoneNumber()));
                 if(centerLeftPane.getChildren().contains(uniL)){
                    centerLeftPane.getChildren().removeAll(uniL,mentorL);
                    centerRightPane.getChildren().removeAll(uniTf,mentorTf);
                }
                if(centerLeftPane.getChildren().contains(emailL)){
                    centerLeftPane.getChildren().removeAll(emailL);
                    centerRightPane.getChildren().removeAll(emailTf);
                }
                if(!centerLeftPane.getChildren().contains(phoneL)) {
                    centerLeftPane.getChildren().add(phoneL);
                    centerRightPane.getChildren().add(phoneTf);
                }
            }else if(staff instanceof Engineering){
                Engineering engineering = (Engineering) staff;
                emailTf.setText(engineering.getEmail());
                comboBox.getSelectionModel().select("Engineering");
                if(centerLeftPane.getChildren().contains(uniL)){
                    centerLeftPane.getChildren().removeAll(uniL,mentorL);
                    centerRightPane.getChildren().removeAll(uniTf,mentorTf);
                }
                if(centerLeftPane.getChildren().contains(phoneL)) {
                    centerLeftPane.getChildren().remove(phoneL);
                    centerRightPane.getChildren().remove(phoneTf);
                }
                if(!centerLeftPane.getChildren().contains(emailL)){
                    centerLeftPane.getChildren().add(emailL);
                    centerRightPane.getChildren().add(emailTf);
                }

            }else if(staff instanceof  StudentInternships){
                StudentInternships studentInternships = (StudentInternships) staff;
                uniTf.setText(studentInternships.getUnivercityName());
                mentorTf.setValue(studentInternships.getMentor());

                comboBox.getSelectionModel().select("Student Internships");
                if(centerLeftPane.getChildren().contains(phoneL)) {
                    centerLeftPane.getChildren().remove(phoneL);
                    centerRightPane.getChildren().remove(phoneTf);
                }
                if(centerLeftPane.getChildren().contains(emailL)){
                    centerLeftPane.getChildren().remove(emailL);
                    centerRightPane.getChildren().remove(emailTf);
                }
                if(!centerLeftPane.getChildren().contains(uniL)){
                    centerLeftPane.getChildren().addAll(uniL,mentorL);
                    centerRightPane.getChildren().addAll(uniTf,mentorTf);
                }
            }

       comboBox.setDisable(true);
            salaryTf.setDisable(true);

        centerMainPane.getChildren().addAll(centerLeftPane,centerRightPane);

        HBox bottomPane =  new HBox(100);
        bottomPane.setAlignment(Pos.BASELINE_LEFT);

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

        Button backBtn =  new Button("Back");
        backBtn.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        backBtn.setOnMouseEntered(e->{
            backBtn.setStyle("-fx-background-color:#ffff;-fx-text-fill:#060890;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        backBtn.setOnMouseExited(e->{
            backBtn.setStyle("-fx-background-color:#060890;-fx-text-fill:#ffff;-fx-font-size:18px;-fx-border-color:#060890;");
        });
        backBtn.setMinWidth(100);
        backBtn.setCursor(Cursor.HAND);
        backBtn.setOnAction(e->{
            goBack();
        });

        bottomPane.getChildren().add(backBtn);


        updateStaff.setOnAction(e->{
            String name =  nameTf.getText();
            double salary ;
            LocalDate startDate = startDateTf.getValue();

            if(name.equals("") || salaryTf.getText().equals("") || startDate==null){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please Enter All the Values");
                alert.setContentText("You can't Submit the Empty values");
                alert.show();
            }else {
                salary = Double.parseDouble(salaryTf.getText());
                if(comboBox.getSelectionModel().getSelectedItem().equals("Administration")){
                    long phone = Long.parseLong(phoneTf.getText());
                    Staff staff =  new Administration(name,salary,startDate,phone);
                    staff.setId(this.staff.getId());
                    controller.setStaff(staff);
                    if(controller.updateStaff()){
                        Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Staff Updation");
                        alert.setHeaderText("This Staff is Successfully Updated in database");
                        alert.setContentText("Staff is Updated in the Database");
                        Optional<ButtonType> op = alert.showAndWait();
                        if(op.get()==ButtonType.OK){
                            goBack();
                        }

                    }else{
                        Alert alert =  new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("There is some SQL Error while Adding");
                        alert.setContentText("This Staff Can't be added due to some SQL Exceptions");
                        alert.show();
                    }
                }else if(comboBox.getSelectionModel().getSelectedItem().equals("Engineering")){
                    String email = emailTf.getText();
                    Staff staff =  new Engineering(name,salary,startDate,email);
                    staff.setId(this.staff.getId());
                    controller.setStaff(staff);
                    if(controller.updateStaff()){
                        Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Staff Updation");
                        alert.setHeaderText("This Staff is Successfully Updated in database");
                        alert.setContentText("Staff is Updated in the Database");
                        Optional<ButtonType> op = alert.showAndWait();
                        if(op.get()==ButtonType.OK){
                            goBack();
                        }
                    }else{
                        Alert alert =  new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("There is some SQL Error while Adding");
                        alert.setContentText("This Staff Can't be added due to some SQL Exceptions");
                        alert.show();
                    }
                }else if(comboBox.getSelectionModel().getSelectedItem().equals("Student Internships")){
                    String uni = uniTf.getText();
                    Staff mentor = mentorTf.getValue();
                    Staff staff =  new StudentInternships(name,salary,startDate,uni,mentor);
                    staff.setId(this.staff.getId());
                    controller.setStaff(staff);
                    if(controller.updateStaff()){
                        Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Staff Updation");
                        alert.setHeaderText("This Staff is Successfully Updated in database");
                        alert.setContentText("Staff is Updated in the Database");
                        Optional<ButtonType> op = alert.showAndWait();
                        if(op.get()==ButtonType.OK){
                            goBack();
                        }
                    }else{
                        Alert alert =  new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("There is some SQL Error while Adding");
                        alert.setContentText("This Staff Can't be added due to some SQL Exceptions");
                        alert.show();
                    }
                }
            }
        });

        bottomPane.setPadding(new Insets(20,20,20,20));
        bottomPane.getChildren().add(updateStaff);




        mainPain.setCenter(centerMainPane);
        mainPain.setTop(topPane);
        mainPain.setBottom(bottomPane);


    }


    public void goBack(){
        Stage stage = (Stage) this.mainPain.getScene().getWindow();
        MainController controller1 = new MainController();
        MainView mainView =  new MainView(controller1);
        Scene scene =  new Scene(mainView.getView());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method Returns A JAVAFX View
     * @return JavaFx View
     */
    public BorderPane getView(){
        return mainPain;
    }
}
