package entities;

import entities.manyToManyWithRel.Prodotto;
import org.junit.Test;
import utils.TestUtil;

import javax.persistence.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/***
 * tested on entity: Prodotto
 */

//Merge vs Persist--> se persisti un'entità e poi gli fai delle modifiche queste vengono fatte anche a db,
// se invece fai merge lui salva a db e poi puoi fare 50 modifiche ma finche non richiami il merge queste non vengono salvate
public class CrudOnSingleEntityTest extends TestUtil {
    @Test
    public void testWrite() {
        Prodotto p = new Prodotto();
        Integer result = null;
        try {
            result = findMax();
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            if (result != null) {
                assertEquals((result + 1), p.getId());
            }
        }
    }

    @Test
    public void deleteMax() {

        Integer result = null;
        result = findMax();
        if (result != null) {
            try {
                Prodotto p = em.find(Prodotto.class, result);
                em.getTransaction().begin();
                em.remove(p);
                em.getTransaction().commit();
                assertNull(em.find(Prodotto.class, result));
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                em.getTransaction().rollback();
            }
        }

    }

    private Integer findMax() {
        Integer result;
        Query query = em.createQuery("SELECT p.id FROM Prodotto p where p.id=(select max(p.id) from p)");
        List results = query.getResultList();
        if (!results.isEmpty()) {
            result = (Integer) results.get(0);
            System.out.println("previous id: " + result);
        } else {
            result = null;
        }
        return result;
    }
}
