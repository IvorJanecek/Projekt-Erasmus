package zavrsni.erasmus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Fakultet.
 */
@Entity
@Table(name = "fakultet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fakultet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "zemlja", nullable = false)
    private String zemlja;

    @NotNull
    @Column(name = "grad", nullable = false)
    private String grad;

    @NotNull
    @Column(name = "adresa", nullable = false)
    private String adresa;

    @OneToMany(mappedBy = "fakultet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "fakultet", "mobilnost", "natjecaj" }, allowSetters = true)
    private Set<Prijava> prijavas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fakultet id(Long id) {
        this.setId(id);
        return this;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class FakultetHasPrijaveException extends RuntimeException {

        public FakultetHasPrijaveException(String message) {
            super(message);
        }
    }

    @PreRemove
    private void preventDeleteIfHasPrijave() {
        if (!prijavas.isEmpty()) {
            throw new Fakultet.FakultetHasPrijaveException("Nije moguće izbrisati fakultet jer se koristi u prijavi.");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Fakultet name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZemlja() {
        return this.zemlja;
    }

    public Fakultet zemlja(String zemlja) {
        this.setZemlja(zemlja);
        return this;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public String getGrad() {
        return this.grad;
    }

    public Fakultet grad(String grad) {
        this.setGrad(grad);
        return this;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public Fakultet adresa(String adresa) {
        this.setAdresa(adresa);
        return this;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Set<Prijava> getPrijavas() {
        return this.prijavas;
    }

    public void setPrijavas(Set<Prijava> prijavas) {
        if (this.prijavas != null) {
            this.prijavas.forEach(i -> i.setFakultet(null));
        }
        if (prijavas != null) {
            prijavas.forEach(i -> i.setFakultet(this));
        }
        this.prijavas = prijavas;
    }

    public Fakultet prijavas(Set<Prijava> prijavas) {
        this.setPrijavas(prijavas);
        return this;
    }

    public Fakultet addPrijava(Prijava prijava) {
        this.prijavas.add(prijava);
        prijava.setFakultet(this);
        return this;
    }

    public Fakultet removePrijava(Prijava prijava) {
        this.prijavas.remove(prijava);
        prijava.setFakultet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fakultet)) {
            return false;
        }
        return id != null && id.equals(((Fakultet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fakultet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zemlja='" + getZemlja() + "'" +
            ", grad='" + getGrad() + "'" +
            ", adresa='" + getAdresa() + "'" +
            "}";
    }
}
