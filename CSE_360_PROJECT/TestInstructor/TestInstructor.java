package TestInstructor;

import org.junit.Test;

import InstructorPage.InstructorPageHandler;

import static org.junit.Assert.assertEquals;
import java.sql.SQLException;

public class TestInstructor {

    @Test	 // testing for invalid student ID
    public void w1() throws SQLException {
        String result = InstructorPageHandler.addStudentTest(-1, 1);
        assertEquals("Student ID must be positive", result);
    }

    @Test      // testing for invalid class ID
    public void w2() throws SQLException {
        String result = InstructorPageHandler.addStudentTest(1, -1);
        assertEquals("Class ID must be positive", result);
    }

    @Test      // testing if the student is in the class already
    public void w3() throws SQLException {
        String result = InstructorPageHandler.addStudentTest(1, 3);
        assertEquals("Student is already enrolled in this class", result);
    }

    @Test      // testing the actuall enrollment of the student for the class
    public void w4() throws SQLException {
        String result = InstructorPageHandler.addStudentTest(2, 3);
        assertEquals("Student enrolled successfully", result);
    }

    @Test      // testing with an incorrect  student ID
    public void w5() throws SQLException {
        String result = InstructorPageHandler.removeStudentTest(-1, 3);
        assertEquals("Student ID must be positive", result);
    }

    @Test      // testing with an  incorrect class ID
    public void w6() throws SQLException {
        String result = InstructorPageHandler.removeStudentTest(2, -4);
        assertEquals("Class ID must be positive", result);
    }

    @Test      // testing to see if the student is in the class
    public void w7() throws SQLException {
        String result = InstructorPageHandler.removeStudentTest(2, 1);
        assertEquals("Student is not enrolled in this class", result);
    }

    @Test      // testing for the actual removal
    public void w8() throws SQLException {
        String result = InstructorPageHandler.removeStudentTest(2, 3);
        assertEquals("Student removed successfully", result);
    }
}
