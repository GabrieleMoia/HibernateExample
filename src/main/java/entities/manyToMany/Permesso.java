package entities.manyToMany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permesso")
public class Permesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permesso")
    private int id;

    @Column(name = "tipo", length = 20)
    private String tipo;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "lavoratore_permesso",
            joinColumns = @JoinColumn(name = "id_permesso"),
            inverseJoinColumns = @JoinColumn(name = "codice_fiscale")
    )
    private List<Lavoratore> lavoratori = new ArrayList<Lavoratore>();

    public Permesso(){}

    public Permesso(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Lavoratore> getLavoratori() {
        return lavoratori;
    }

    public void setLavoratori(List<Lavoratore> lavoratori) {
        this.lavoratori = lavoratori;
    }
}
