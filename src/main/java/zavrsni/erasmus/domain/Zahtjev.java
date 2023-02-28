package zavrsni.erasmus.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "zahtjev")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Zahtjev implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "natjecaj_id")
    private Natjecaj natjecaj;

    public Natjecaj getNatjecaj() {
        return natjecaj;
    }

    public void setNatjecaj(Natjecaj natjecaj) {
        this.natjecaj = natjecaj;
    }

    public Zahtjev() {}

    @Override
    public String toString() {
        return "Zahtjev{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public Zahtjev id(Long id) {
        this.setId(id);
        return this;
    }

    private void setId(Long id) {
        this.id = id;
    }
}
