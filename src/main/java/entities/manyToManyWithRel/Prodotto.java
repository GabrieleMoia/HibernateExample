package entities.manyToManyWithRel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prodotto")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProdotto")
    private int id;

    @Column (name="nome")
    private String nome;

    @OneToMany(mappedBy = "primaryKey.prodotto", cascade = CascadeType.ALL)
    private List<Acquisto> acquisti =new ArrayList<Acquisto>();

    public int getId() {
        return id;
    }

    public Prodotto() {
    }

    public Prodotto(String nome) {
        this.nome = nome;
    }
    public Prodotto(int id){
        return;
    }
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){this.nome = nome;}

    public List<Acquisto> getAcquisti() {
        return acquisti;
    }

    public void setAcquisti(List<Acquisto> acquisti) {
        this.acquisti = acquisti;
    }

    public void addAcquisto (Acquisto a){
        acquisti.add(a);
    }
}
