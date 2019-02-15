package entities.manyToManyWithRel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fornitura")
@AssociationOverrides({//to override the mappings for the prodotto and fornitore attributes of the composite key.
        @AssociationOverride(name = "primaryKey.fornitore",
                joinColumns = @JoinColumn(name = "id_fornitore")),
        @AssociationOverride(name = "primaryKey.prodotto",
                joinColumns = @JoinColumn(name = "id_prodotto"))})
public class Fornitura {

    @EmbeddedId //needed to embed a composite-id class as the primary key of this mapping.
    private FornitoreProdottoKey primaryKey = new FornitoreProdottoKey();

    @Column(name = "quantita")
    private int quantita;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date date;

    public FornitoreProdottoKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(FornitoreProdottoKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Transient// Hibernate doesn’t try to map these getters. These getters are provided for convenience in case we want to obtain a specific side of the relationship
    public Prodotto getProdotto(){
        return primaryKey.getProdotto();
    }

    public void setProdotto(Prodotto prodotto){
        primaryKey.setProdotto(prodotto);
    }

    @Transient
    public Fornitore getFornitore(){
        return primaryKey.getFornitore();
    }

    public void setFornitore(Fornitore fornitore){
        primaryKey.setFornitore(fornitore);
    }
}
