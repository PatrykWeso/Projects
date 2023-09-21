package controller;

import DBA.DBManager;
import model.Staff;


/**
 * This controller is responsible for Staff Updation
 */
public class UpdateController {
    private Staff staff;

    /**
     * Update Staff Controller
     * @param staff Staff Object to be updated
     */
    public UpdateController(Staff staff) {
        this.staff = staff;
    }


    /**
     * Staff Setter
     * @param staff Staff Object
     */
    public void setStaff(Staff staff){
        this.staff = staff;
    }

    /**
     * This method Actually responsible for Staff updation it takes a staff Object then merge it with the old one
     * @return Boolean if staff is Updated or not
     */
    public boolean updateStaff(){

        DBManager.entityManager.getTransaction().begin();
        if(staff!=null){
             DBManager.entityManager.find(Staff.class,staff.getId());
             DBManager.entityManager.merge(staff);
        }else {
            return false;
        }
        DBManager.entityManager.getTransaction().commit();
        return true;
    }
}
