package zavrsni.erasmus.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "natjecaj_id")
    private Natjecaj natjecaj;

    @PreRemove
    private void preventDeleteIfNatjecajHasPrijavas() {
        if (natjecaj != null && !natjecaj.getPrijavas().isEmpty()) {
            throw new NatjecajHasPrijavasException("Nije moguce izbrisati zahtjev jer natjecaj ima prijave u sebi.");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class NatjecajHasPrijavasException extends RuntimeException {

        public NatjecajHasPrijavasException(String message) {
            super(message);
        }
    }

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
