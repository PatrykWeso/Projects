package controller;

import DBA.DBManager;
import model.Staff;

/**
 * Add Controller Handle Staff Submission
 * This controller Handle Staff insertion with Database
 */
public class AddController {

    private Staff staff;

    /**
     * Add Controller Constructor
     */
    public AddController() {
    }

    /**
     * Staff Setter
     * @param staff Staff Object
     */
    public void setStaff(Staff staff){
        this.staff = staff;
    }

    /**
     * Add Staff method adds current staff in database and then return true if added successfully
     * @return Boolean, true if added else false
     */
    public boolean addStaff(){
        DBManager.entityManager.getTransaction().begin();
        if(staff!=null) {
            DBManager.entityManager.persist(staff);
        }else {
            return false;
        }
        DBManager.entityManager.getTransaction().commit();
        return true;
    }
}
