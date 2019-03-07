package entities.oneToMany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name =" utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idUtente")
    private int id;

    @Column (name="nome")
    private String nome;

    @Column (name="cognome")
    private String cognome;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Ordine> ordini = new ArrayList<Ordine>();

    public Utente(){

    }

    public Utente(String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getCognome (){return cognome;}
    public void setCognome(String cognome){this.cognome = cognome;}
    public List<Ordine> getOrdini(){return ordini; }
    public void setOrdini(List<Ordine> ordini){this.ordini = ordini;}

    @PreRemove
    public void removeForeignKey() {
        for (Ordine ordine : ordini) {
            ordine.setUtente(null);
        }
    }


}
