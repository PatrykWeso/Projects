package model;

import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Staff Class basically its a model and a Table in database
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Staff {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double salary;
    private LocalDate startDate;

    /**
     * No Argument Constructor for Staff
     */
    protected Staff() {
    }

    /**
     * Fully argumented Constructor for Staff
     * @param name
     * @param salary
     * @param startDate
     */
    public Staff(String name, double salary, LocalDate startDate) {
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    /**
     * Getter for Staff ID
     * @return Staff's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Staff ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Getter for Staff's Name
     * @return Staff's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter For Staff Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Getter for Staffs Salary
     * @return Staff Salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Setter for Staff Salary
     * @param salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Getter for Staff Started Date
     * @return LocalDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Setter for Staff Started Date
     * @param startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * An Abstract method that gives a Raise to Satff's Salary
     */
    public abstract void getRaise();
    @Override
    public String toString() {
             return name;
    }
}
