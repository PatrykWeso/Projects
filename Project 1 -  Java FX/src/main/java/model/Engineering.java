package model;

import javax.persistence.Entity;

import java.time.LocalDate;


/**
 * Engineering Class is a Child of Staff class Behaves as a model and table
 */
@Entity
public class Engineering extends Staff{

    private String email;

    /**
     * no-argument constructor
     */
    public Engineering() {
    }
    /**
     * An Overide and abstract method that gives a Raise to Administration's Salary
     */
    @Override
    public void getRaise() {
        double raise = 7.5 * getSalary()/100;
        double newSalary = raise+getSalary();
        setSalary(newSalary);
    }


    /**
     * Fully Argumented Constructor
     * @param name
     * @param salary
     * @param startDate
     * @param email
     */
    public Engineering(String name, double salary, LocalDate startDate, String email) {
        super(name, salary, startDate);
        this.email = email;
    }

    /**
     * Getter for Email
     * @return String Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for Engineering's Email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
