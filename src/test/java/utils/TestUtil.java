package utils;

import Utility.Em;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;

public class TestUtil {

    protected static EntityManager em;

    @BeforeClass
    public static void getEM() {
        em = Em.createEntityManager();
    }

    @AfterClass
    public static void closeEm(){
        em.close();
        Em.closeEntityManager();
    }

}
