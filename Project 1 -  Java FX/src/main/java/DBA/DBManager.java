package DBA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This Class is responsible for database connection
 */
public class DBManager {
   public static EntityManagerFactory entityManagerFactory;
    public static EntityManager entityManager;

    /**
     * A static method that connect with database using Java JPA API
     */
    public static void connectMe(){
        entityManagerFactory =  Persistence.createEntityManagerFactory("st");
        entityManager =  entityManagerFactory.createEntityManager();

    }
}
