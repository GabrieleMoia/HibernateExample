package entities;

import entities.manyToMany.Lavoratore;
import entities.manyToMany.Permesso;
import org.junit.Test;
import utils.TestUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/***
 * tested on many to many relation lavoratore -> permesso
 */
public class CrudOnManyToManyEntities extends TestUtil {

    @Test
    public void insertFromProdotto() {
        Lavoratore l1 = new Lavoratore("cod", "nome", "cognome");
        Lavoratore l2 = new Lavoratore("cod2", "nome2", "cognome2");
        Permesso p = new Permesso("tipo");
        List<Lavoratore> list = p.getLavoratori();
        list.add(l1);
        list.add(l2);
        p.setLavoratori(list);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Test
    //inserisce solo un lavoratore ma non i prodotti e le relazioni con il mapped, mentre con il rjoin column su entrambe le clasi si puo utilizzare
    public void insertFromLavoratore() {
        Lavoratore l1 = new Lavoratore("cod4", "nome", "cognome");
        Permesso p = new Permesso("tipo");
        Permesso p2 = new Permesso("tipo2");
        List<Permesso> list = l1.getPermessi();
        list.add(p);
        list.add(p2);
        l1.setPermessi(list);
        em.getTransaction().begin();
        em.persist(l1);
        em.getTransaction().commit();
    }

    @Test
    public void connectSeparatly() {


        Lavoratore l = new Lavoratore("cod5", "nome", "cognome");
        Permesso p = new Permesso("prova");
        em.getTransaction().begin();
        em.persist(l);
        em.persist(p);
        em.getTransaction().commit();

        //per aggiungere una relazione bisgona ripersistere tutto l'oggetto

        Lavoratore l1 = getLavoratore(l.getId());
        Permesso p1 = getPermesso(p.getId());

        //bisogna aggiungere l'associazione a quelle già esistenti altrimenti cancella le associazioni precedenti
        List<Permesso> permessi = new ArrayList<Permesso>();
        if (l1 != null && l1.getPermessi().size() > 0) {
            permessi = l1.getPermessi();
        }
        permessi.add(p1);

        // con questa istruzione cancella le altre associazioni e rimette quella creata
        // l1.setPermessi(permessi);


        em.getTransaction().begin();
        em.merge(l1);
        em.getTransaction().commit();


    }

    //cancella anche dalla tabella di relazione
    @Test
    public void testDeletePermesso() {
        Permesso p = em.find(Permesso.class, 2);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    //se si us il mapped anziche il join column si rompe quando cerchi di cancellare l'entità perchè violi i vincoli di chiave esterna
    @Test
    public void testDeleteLavoratore() {
        Lavoratore l = em.find(Lavoratore.class, "cod4");
        em.getTransaction().begin();
        em.remove(l);
        em.getTransaction().commit();
    }

    //non è possibile updatare una chiave primaria
   /* @Test
    public void updateCodiceFiscale() {
        Lavoratore l = em.find(Lavoratore.class, "cod5");
        l.setId("nuovocodice");
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
       /*Lavoratore l = getLavoratore("cod5");
        Query query = em.createQuery("update Lavoratore l set l.id=:id_nuovo WHERE l.id=:id");
        query.setParameter("id", l.getId());
        query.setParameter("id_nuovo", "codNuovo");
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();

    }*/

    private Permesso getPermesso(int id) {
        Query query = em.createQuery("select p From Permesso p WHERE p.id=:id");
        query.setParameter("id", id);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            Permesso permesso = (Permesso) results.get(0);
            return permesso;
        }
        return null;
    }

    private Lavoratore getLavoratore(String cod) {
        Query query = em.createQuery("select l From Lavoratore l WHERE l.id=:id");
        query.setParameter("id", cod);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            Lavoratore lavoratore = (Lavoratore) results.get(0);
            return lavoratore;
        }
        return null;
    }
}
