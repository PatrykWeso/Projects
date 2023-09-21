package controller;

import DBA.DBManager;
import model.Staff;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Main Controller basically gives a list of all staffs from the database
 */
public class MainController {

    private List<Staff> staff;

    /**
     * Main  controller Constructor
     */
    public MainController() {
        DBManager.entityManager.getTransaction().begin();
        staff=findAll();
        DBManager.entityManager.getTransaction().commit();
    }

    /**
     * This is an stati method it Returns a list of staff after getting from the database
     * @return List of Staff
     */
    public static List<Staff> findAll() {
        CriteriaBuilder cb = DBManager.entityManager.getCriteriaBuilder();
        CriteriaQuery<Staff> cq = cb.createQuery(Staff.class);
        Root<Staff> rootEntry = cq.from(Staff.class);
        CriteriaQuery<Staff> all = cq.select(rootEntry);
        TypedQuery<Staff> allQuery = DBManager.entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    /**
     * This method returns the list of current staffs in the MainController
     * @return List of Staff
     */
    public List<Staff> getStaff() {
        staff=findAll();
        return staff;
    }

    /**
     * Setter for the staff list
     * @param staff
     */
    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }
}
