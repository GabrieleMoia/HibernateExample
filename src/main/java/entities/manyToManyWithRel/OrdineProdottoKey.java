package entities.manyToManyWithRel;

import entities.oneToMany.Ordine;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable // with this annotation this class can be embedded in other classes
public class OrdineProdottoKey implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ordine ordine;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Prodotto prodotto;

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}
