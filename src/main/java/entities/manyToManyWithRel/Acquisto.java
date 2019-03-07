package entities.manyToManyWithRel;

import entities.oneToMany.Ordine;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acquisto")
@AssociationOverrides({//to override the mappings for the prodotto and fornitore attributes of the composite key.
        @AssociationOverride(name = "primaryKey.ordine",
                joinColumns = @JoinColumn(name = "idOrdine")),
        @AssociationOverride(name = "primaryKey.prodotto",
                joinColumns = @JoinColumn(name = "idProdotto"))})
public class Acquisto {

    @EmbeddedId //needed to embed a composite-id class as the primary key of this mapping.
    private OrdineProdottoKey primaryKey = new OrdineProdottoKey();

    public OrdineProdottoKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(OrdineProdottoKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient// Hibernate doesn’t try to map these getters. These getters are provided for convenience in case we want to obtain a specific side of the relationship
    public Prodotto getProdotto(){
        return primaryKey.getProdotto();
    }

    public void setProdotto(Prodotto prodotto){
        primaryKey.setProdotto(prodotto);
    }

    @Transient
    public Ordine getOrdine(){
        return primaryKey.getOrdine();
    }

    public void setOrdine (Ordine ordine){
        primaryKey.setOrdine(ordine);
    }
}
