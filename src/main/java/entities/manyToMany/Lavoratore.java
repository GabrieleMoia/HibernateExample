package entities.manyToMany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lavoratore")
public class Lavoratore {
    @Id
    @Column(name = "codice_fiscale", length = 100)
    private String id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "cognome", length = 100)
    private String cognome;

    // @ManyToMany(mappedBy = "lavoratori") //non permette di inserire i prodotti e la relazione persistendo l'entità lavoratore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "lavoratore_permesso",
            joinColumns = @JoinColumn(name = "codice_fiscale"),
            inverseJoinColumns = @JoinColumn(name = "id_permesso")
    )
    private List<Permesso> permessi = new ArrayList<Permesso>();

    public Lavoratore() {
    }

    public Lavoratore(String codiceFiscale, String nome, String cognome) {
        this.id = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getId() {
        return id;
    }

    public void setId(String cf) {
        id = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<Permesso> getPermessi() {
        return permessi;
    }

    public void setPermessi(List<Permesso> permessi) {
        this.permessi = permessi;
    }
}
