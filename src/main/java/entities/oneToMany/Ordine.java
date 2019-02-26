package entities.oneToMany;

import entities.manyToManyWithRel.Acquisto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordine") // it works also for view with this name
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrdine")
    private int id;

    // one To one and many to one should be always lazy (the other are default lazy)--> eager load automatically
    // the entities relationed for lazy they dug up the values when you effectively call them
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="idUtente")
    private Utente utente;

    @OneToMany(mappedBy = "primaryKey.ordine", cascade = CascadeType.ALL)
    private List<Acquisto> acquisti = new ArrayList<Acquisto>();


    public Ordine(){

    }

    public int getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Acquisto> getAcquisto() {
        return acquisti;
    }

    public void setAcquisto(List<Acquisto> acquisto) {
        this.acquisti = acquisto;
    }

    public void addAcquisto(Acquisto a){
        acquisti.add(a);
    }
}
