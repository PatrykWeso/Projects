package App;

import DBA.DBManager;
import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Administration;
import model.Engineering;
import model.Staff;
import model.StudentInternships;
import view.MainView;

import java.time.LocalDate;

/**
 * This Class is The Main class of the Application
 */
public class App extends Application {

    /**
     * Application main method
     * @param args
     */
    public static void main(String[] args) {
        DBManager.connectMe();
        fill();
        launch(args);
    }


    /**
     * This method Fills some Records in Database
     */
    public static void fill(){
        Staff st = new Administration();
        st.setName("Ali Hamza");
        st.setSalary(313);
        st.setStartDate(LocalDate.now());
        Administration administration = (Administration) st;
        administration.setPhoneNumber(32421321);

        Staff st2 = new Engineering();
        st2.setName("Ali Hamza");
        st2.setSalary(313);
        st2.setStartDate(LocalDate.now());
        Engineering eng = (Engineering) st2;
        eng.setEmail("Abdul rehman");



        Staff st1 = new StudentInternships();
        st1.setName("Student");
        st1.setSalary(3423);
        st1.setStartDate(LocalDate.now());
        StudentInternships studentInternships= (StudentInternships) st1;
        studentInternships.setUnivercityName("Comsats");
        studentInternships.setMentor(st);

        DBManager.entityManager.getTransaction().begin();
        DBManager.entityManager.persist(st);
        DBManager.entityManager.persist(st1);
        DBManager.entityManager.persist(st2);
        DBManager.entityManager.getTransaction().commit();
    }

    /**
     * An override Start method from Parent Class Application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        MainController controller= new MainController();
        MainView view = new MainView(controller);
        Scene scene = new Scene(view.getView(),900,700);
        stage.setScene(scene);
        stage.show();
    }
}
