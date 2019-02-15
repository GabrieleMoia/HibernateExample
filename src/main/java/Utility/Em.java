package Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Em {


    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("logistica");

    public static EntityManager createEntityManager(){
        return entityManagerFactory.createEntityManager();
    }


    public static void closeEntityManager(){
        entityManagerFactory.close();
    }



}
