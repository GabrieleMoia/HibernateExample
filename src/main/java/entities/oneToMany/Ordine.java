package entities.oneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordine")
    private int id;

    // one To one and many to one should be always lazy (the other are default lazy)--> eager load automatically
    // the entities relationed for lazy they dug up the values when you effectively call them
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id_corriere")

    private Corriere corriere;

    @Column(name = "id_trasporto")
    private int id_trasporto;

    public Ordine(){

    }

    public int getId() {
        return id;
    }

    public Corriere getCorriere() {
        return corriere;
    }

    public void setCorriere(Corriere corriere) {
        this.corriere = corriere;
    }

    public int getId_trasporto() {
        return id_trasporto;
    }

    public void setId_trasporto(int id_trasporto) {
        this.id_trasporto = id_trasporto;
    }


}
