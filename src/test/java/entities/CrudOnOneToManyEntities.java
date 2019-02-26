package entities;

import entities.oneToMany.Ordine;
import entities.oneToMany.Utente;
import org.junit.Test;
import utils.TestUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/***
 * Tested on 1 to many relation of Utente(1) -> ordine(n)
 */
public class CrudOnOneToManyEntities extends TestUtil {

    //inserire una lista di ordini nel corriere non serve a far scrivere gli ordini già in relazione quando viene scritto un corriere (gli ordini non vengono proprio scritti
    //persistere prima gli ordini atrimenti va in errore
    @Test
    public void insert() {
        Utente utente = new Utente("Gabriele", "Moia");
        Utente utente1 = new Utente("Stefano", "Liberato");
        Ordine o1 = new Ordine();
        Ordine o2 = new Ordine();
        Ordine o3 = new Ordine();
        Ordine o4 = new Ordine();

        o1.setUtente(utente);
        o2.setUtente(utente);
        o3.setUtente(utente1);
        o4.setUtente(utente1);

        try {

            em.getTransaction().begin();
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.persist(o4);

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    //non scrive gli ordini (parte ad N della relazione)
    @Test
    public void insert2() {
        Utente utente = new Utente("Mario", "Rossi");
        Utente utente1 = new Utente("Giulio", "Bianchi");
        Ordine o1 = new Ordine();
        Ordine o2 = new Ordine();
        Ordine o3 = new Ordine();
        Ordine o4 = new Ordine();

        List<Ordine> list =new ArrayList<>();

        list.add(o1);
        list.add(o3);

        utente.setOrdini(list);

        try {
            em.getTransaction().begin();
            em.persist(utente);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    //con id non esistente restituisce una lista vuota
    // ze viene fatto il close dell'entity manager i valori della relazione presi in modo lazy non possono essere letti
    /*@Test
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

    }*/

    @Test
    public void selectCorriereDiOrdine() {
        Query query = em.createQuery("select o From Ordine o WHERE o.id=:id");
        query.setParameter("id", 6);
        List results = query.getResultList();
        em.close();
        if (!results.isEmpty()) {
            Ordine ordine = (Ordine) results.get(0);
            //se usi il debug il corriere sembra a caso ma quando chiami il to string è quello giusto (LAZY)
            Utente utente = ordine.getUtente();
            System.out.println("id_utente: " + utente.getId());
            assertNotNull(utente.getId());
        }
    }

    @Test
    public void updateTest() {
        Query query = em.createQuery("delete From Utente c  WHERE c.id=:id");
        query.setParameter("id", 13);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

    void flushAndClear() {
        em.flush();
        em.clear();
    }

    @Test
    public void getUtentiinEm(){
        for(int i = 12; i < 100; i++){
            em.getTransaction().begin();
            Utente u = em.find(Utente.class, i);
            em.getTransaction().commit();
            if(u != null) {
                System.out.println(u.getId());
            }
        }
    }
}