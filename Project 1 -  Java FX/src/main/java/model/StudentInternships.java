package model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDate;

/**
 * StudentInternships Class is a Child of Staff class Behaves as a model and table
 */
@Entity
public class StudentInternships extends Staff {

    private String univercityName;
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Staff mentor;

    /**
     * no-argument Constructor
     */
    public StudentInternships() {
    }

    /**
     * An Overide and abstract method that gives a Raise to Administration's Salary
     */
    @Override
    public void getRaise() {
        double raise = 2 * getSalary()/100;
        double newSalary = raise+getSalary();
        setSalary(newSalary);
    }

    /**
     * Fully Argument Constructor
     * @param name
     * @param salary
     * @param startDate
     * @param univercityName
     * @param mentor
     */
    public StudentInternships(String name, double salary, LocalDate startDate, String univercityName, Staff mentor) {
        super(name, salary, startDate);
        this.univercityName = univercityName;
        this.mentor = mentor;
    }

    /**
     * Getter for University Name
     * @return University Name
     */
    public String getUnivercityName() {
        return univercityName;
    }

    /**
     * Setter for University Name
     * @param univercityName
     */
    public void setUnivercityName(String univercityName) {
        this.univercityName = univercityName;
    }


    /**
     * Getter for Student Mentor
     * @return Staff Object
     */
    public Staff getMentor() {
        return mentor;
    }

    /**
     * Setter for Student's Mentor
     * @param mentor
     */
    public void setMentor(Staff mentor) {
        this.mentor = mentor;
    }
}
