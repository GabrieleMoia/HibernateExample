package entities;

import entities.manyToManyWithRel.Acquisto;
import entities.manyToManyWithRel.Prodotto;
import entities.oneToMany.Ordine;
import entities.oneToMany.Utente;
import org.junit.Test;
import utils.TestUtil;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/***
 * crud on many to many entities that have an attribute for the reletions pordotto->fornitore (relationed by fornitura that have quantità and data as attributes)
 */
public class CrudOnManyToManyEntitiesWithRelationAttrubutes extends TestUtil {

    /*usare le altre 2
    @Test
    public void insert(){
        Prodotto p = new Prodotto();
        Fornitore f = new Fornitore("ciao");
        Fornitura fornitura= new Fornitura();
        fornitura.setFornitore(f);
        fornitura.setProdotto(p);
        fornitura.setDate(new Date());
        fornitura.setQuantita(3);
        em.getTransaction().begin();
        em.persist(f);
        em.persist(p);
        em.persist(fornitura);
        em.getTransaction().commit();
    }*/

    @Test
    public void insert1() {
        Prodotto p = new Prodotto("Cellulare");
        Utente u = new Utente("Marco","Moia");
        Ordine o = new Ordine();
        Acquisto acquisto = new Acquisto();
        o.setUtente(u);
        acquisto.setOrdine(o);
        acquisto.setProdotto(p);
        p.addAcquisto(acquisto);
        em.getTransaction().begin();
        em.persist(o);
        em.persist(p);
        em.getTransaction().commit();
    }

    /*@Test
    public void insert2() {
        Prodotto p = new Prodotto();
        Fornitore f = new Fornitore("ciao");
        Fornitura fornitura = new Fornitura();
        fornitura.setFornitore(f);
        fornitura.setProdotto(p);
        fornitura.setDate(new Date());
        fornitura.setQuantita(3);
        f.addFornitura(fornitura);
        em.getTransaction().begin();
        em.persist(p);
        em.persist(f);
        em.getTransaction().commit();
    }*/

    @Test
    public void insertAlreadyExisting() {
        Prodotto p = getProdotto(1);
        Ordine o = getOrdine(21);
        Acquisto acquisto = new Acquisto();
        acquisto.setProdotto(p);
        acquisto.setOrdine(o);
        em.getTransaction().begin();
        em.persist(acquisto);
        em.getTransaction().commit();
    }

    //cancella anche dalla tabella di relazione
    /*@Test
    public void testDeleteOrdine() {
        Ordine f = em.find(Ordine.class, 21);
        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
    }*/

    //se si us il mapped anziche il join column si rompe quando cerchi di cancellare l'entità perchè violi i vincoli di chiave esterna
    @Test
    public void testDeleteProdotto() {
        /*Prodotto p = em.find(Prodotto.class, 1);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();*/

        Query query = em.createQuery("delete From Prodotto c  WHERE c.id=:id");
        query.setParameter("id", 1);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

    /*@Test
    public void select() {

        Prodotto p = getProdotto(1);
        List<Fornitura> list = p.getForniture();
        list.forEach(x -> {
            System.out.println(x.getFornitore().getId());
        });
    }*/


    private Prodotto getProdotto(int id) {
        Query query = em.createQuery("select p From Prodotto p WHERE p.id=:id");
        query.setParameter("id", id);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            Prodotto prodotto = (Prodotto) results.get(0);
            return prodotto;
        }
        return null;
    }

    private Ordine getOrdine(int id) {
        Query query = em.createQuery("select o From Ordine o WHERE o.id=:id");
        query.setParameter("id", id);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            Ordine ordine = (Ordine) results.get(0);
            return ordine;
        }
        return null;
    }

}
