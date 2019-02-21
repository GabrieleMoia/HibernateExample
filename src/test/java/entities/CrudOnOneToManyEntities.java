package entities;

import entities.oneToMany.Corriere;
import entities.oneToMany.Ordine;
import org.junit.Test;
import utils.TestUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/***
 * Tested on 1 to many relation of Corriere(1) -> ordine(n)
 */
public class CrudOnOneToManyEntities extends TestUtil {

    //inserire una lista di ordini nel corriere non serve a far scrivere gli ordini già in relazione quando viene scritto un corriere (gli ordini non vengono proprio scritti
    //persistere prima gli ordini atrimenti va in errore
    @Test
    public void insert() {
        Corriere cor1 = new Corriere("c1", "via", "citta", "6/a", "20861");
        Corriere cor2 = new Corriere("c2", "viale", "paese", "6/c", "20800");
        Ordine o1 = new Ordine();
        Ordine o2 = new Ordine();
        Ordine o3 = new Ordine();
        Ordine o4 = new Ordine();

        o1.setId_trasporto(20);
        o1.setCorriere(cor1);

        o2.setId_trasporto(19);
        o2.setCorriere(cor1);

        o3.setId_trasporto(17);
        o3.setCorriere(cor2);

        o4.setId_trasporto(11);


        try {

            em.getTransaction().begin();
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.persist(o4);

          //  em.persist(cor1);
            //em.persist(cor2);

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    //non scrive gli ordini (parte ad N della relazione)
    @Test
    public void insert2() {
        Corriere cor1 = new Corriere("c1", "via", "citta", "6/a", "20861");
        Corriere cor2 = new Corriere("c2", "viale", "paese", "6/c", "20800");
        Ordine o1 = new Ordine();
        Ordine o2 = new Ordine();
        Ordine o3 = new Ordine();
        Ordine o4 = new Ordine();

        o1.setId_trasporto(20);


        o2.setId_trasporto(19);


        o3.setId_trasporto(17);


        o4.setId_trasporto(11);

        List<Ordine> list =new ArrayList<>();

        list.add(o1);
        list.add(o3);

        cor1.setOrdini(list);


        try {

            em.getTransaction().begin();

            em.persist(cor1);

            //  em.persist(cor1);
            //em.persist(cor2);

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    //con id non esistente restituisce una lista vuota
    // ze viene fatto il close dell'entity manager i valori della relazione presi in modo lazy non possono essere letti
    @Test
    public void selectOrdiniDiCorriere() {
        Query query = em.createQuery("select c From Corriere c WHERE c.id=:id");
       // Query query = em.createQuery("select c From Corriere c JOIN FETCH c.ordini WHERE c.id=:id"); da usare per avere le relazioni anche quando lazy e con entity manager chiuso
        query.setParameter("id", 2);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            Corriere corriere = (Corriere) results.get(0);
            assertTrue(corriere.getOrdini().size() > 0);
            assertNotNull(corriere.getOrdini().get(0).getId());
        }

    }

    @Test
    public void selectCorriereDiOrdine() {
        Query query = em.createQuery("select o From Ordine o WHERE o.id=:id");
        query.setParameter("id", 2);
        List results = query.getResultList();
        em.close();
        if (!results.isEmpty()) {
            Ordine ordine = (Ordine) results.get(0);
            //se usi il debug il corriere sembra a caso ma quando chiami il to string è quello giusto (LAZY)
            Corriere corriere = ordine.getCorriere();
            System.out.println("corriere: " + corriere.toString());
            assertNotNull(corriere.getId());
        }

    }

    @Test
    public void updateTest() {
        /*Query query = em.createQuery("delete From Corriere c  WHERE c.id=:id");
        query.setParameter("id", 1);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();*/
        Corriere c = em.find(Corriere.class, 100);
        super.closeEm();
        super.getEM();
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }


}