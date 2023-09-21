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
 * This Class is Responsible for Showing Staff's Details in JavaFx View
 */
public class StaffDetailsView {

    BorderPane mainPain;
    Staff staff;

    /**
     * A StaffDetailsView Constructor
     * @param staff
     */
    public StaffDetailsView(Staff staff) {
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
        Label title =  new Label("Staff Details");
        title.setStyle("-fx-text-fill:#060890;-fx-font-size:20px;-fx-font-weight:bold;");
        title.setUnderline(true);
        title.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(20,20,20,20));
        topPane.getChildren().add(title);

        //
        HBox centerMainPane = new HBox(30);

        //

        TextField comboBox =  new TextField();
        comboBox.setMinHeight(30);

        comboBox.setEditable(false);

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
        nameTf.setEditable(false);

        TextField salaryTf =  new TextField();
        salaryTf.setPromptText("Salary");
        salaryTf.setMinHeight(30);
        salaryTf.setText(String.valueOf(staff.getSalary()));
        salaryTf.setEditable(false);

        DatePicker startDateTf =  new DatePicker();
        startDateTf.setValue(LocalDate.now());
        startDateTf.setMinHeight(30);
        startDateTf.setValue(staff.getStartDate());
        startDateTf.setEditable(false);

        TextField phoneTf =  new TextField();
        phoneTf.setPromptText("Phone Number");
        phoneTf.setMinHeight(30);
        phoneTf.setEditable(false);

        TextField emailTf =  new TextField();
        emailTf.setPromptText("Email");
        emailTf.setMinHeight(30);
        emailTf.setEditable(false);

        TextField uniTf =  new TextField();
        uniTf.setPromptText("University Name");
        uniTf.setMinHeight(30);
        uniTf.setEditable(false);

        TextField mentorTf =  new TextField();
        mentorTf.setMinHeight(30);
        mentorTf.setEditable(false);
        mentorTf.setEditable(false);

        centerLeftPane.getChildren().addAll(staffTypeL,nameL,salaryL,startDateL,phoneL,emailL,uniL,mentorL);
        centerRightPane.getChildren().addAll(comboBox,nameTf,salaryTf,startDateTf,phoneTf,emailTf,uniTf,mentorTf);


        if(staff instanceof Administration){
            comboBox.setText("Administration");
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
            comboBox.setText("Engineering");
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
            mentorTf.setText(studentInternships.getMentor().getName());

            comboBox.setText("Student Internships");
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

        HBox bottomPane =  new HBox();
        bottomPane.setAlignment(Pos.CENTER);


        Button backBtn =  new Button("close");
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
           Stage stage = (Stage) this.mainPain.getScene().getWindow();
           stage.close();
        });

        bottomPane.getChildren().add(backBtn);
        bottomPane.setPadding(new Insets(20,20,20,20));
        mainPain.setCenter(centerMainPane);
        mainPain.setTop(topPane);
        mainPain.setBottom(bottomPane);


    }


    /**
     * This method Returns the Javafx View
     * @return JavaFx View
     */
    public BorderPane getView(){
        return mainPain;
    }
}
