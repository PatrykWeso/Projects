import model.Administration;
import model.Staff;
import model.StudentInternships;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestStudentIntern {

    @Test
    public void testStudentRaise(){
        Staff student =  new StudentInternships("ABC",100, LocalDate.now(),"uiversity",null);
        student.setId(1);
        student.getRaise();
        Assertions.assertTrue(student.getSalary()==102);
    }
}