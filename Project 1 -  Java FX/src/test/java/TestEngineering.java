import model.Administration;
import model.Engineering;
import model.Staff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestEngineering {
    @Test
    public void testEngineeringRaise(){
        Staff engineer =  new Engineering("ABC",100, LocalDate.now(),"engineering@gmail.com");
        engineer.setId(1);
        engineer.getRaise();
        Assertions.assertTrue(engineer.getSalary()==107.5);
    }
}