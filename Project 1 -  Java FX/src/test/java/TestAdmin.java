import model.Administration;
import model.Staff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestAdmin {

    @Test
    public void testAdminRaise(){
        Staff administration =  new Administration("ABC",100, LocalDate.now(),32423232);
        administration.setId(1);
        administration.getRaise();
        Assertions.assertTrue(administration.getSalary()==105);
    }
}
