package entities.manyToManyWithRel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prodotto")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodotto")
    private int id;

    @OneToMany(mappedBy = "primaryKey.prodotto", cascade = CascadeType.ALL)
    private List<Fornitura> forniture =new ArrayList<Fornitura>();

    public int getId() {
        return id;
    }

    public Prodotto() {
    }

    public List<Fornitura> getForniture() {
        return forniture;
    }

    public void setForniture(List<Fornitura> forniture) {
        this.forniture = forniture;
    }

    public void addFornitura(Fornitura f){
        forniture.add(f);
    }
}
