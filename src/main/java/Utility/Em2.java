package Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Em2 {


    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("commercio");

    public static EntityManager createEntityManager(){
        return entityManagerFactory.createEntityManager();
    }


    public static void closeEntityManager(){
        entityManagerFactory.close();
    }



}