package model;

import javax.persistence.Entity;
import java.time.LocalDate;


/**
 * Administration Class Child of Staff Class Behaves as a model and a table
 */
@Entity
public class Administration extends Staff{

    private long phoneNumber;

    /**
     * no-argument Constructor for Administration
     */
    public Administration() {
    }

    /**
     * An Overide and abstract method that gives a Raise to Administration's Salary
     */
    @Override
    public void getRaise() {
        double raise = 5 * getSalary()/100;
        double newSalary = raise+getSalary();
        setSalary(newSalary);
    }

    /**
     * Fully Argumented Constructor for Administration
     * @param name
     * @param salary
     * @param startDate
     * @param phoneNumber
     */
    public Administration(String name, double salary, LocalDate startDate, long phoneNumber) {
        super(name, salary, startDate);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for PhoneNumber of Administration
     * @return
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for PhoneNumber of Administration
     * @param phoneNumber
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
