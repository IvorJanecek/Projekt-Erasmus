package zavrsni.erasmus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import zavrsni.erasmus.domain.enumeration.Korisnik;
import zavrsni.erasmus.domain.enumeration.Status;

/**
 * A Natjecaj.
 */
@Entity
@Table(name = "natjecaj")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Natjecaj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "datum_od")
    private LocalDate datumOd;

    @Column(name = "datum_do")
    private LocalDate datumDo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "korisnik")
    private Korisnik korisnik;

    @OneToMany(mappedBy = "natjecaj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "fakultet", "mobilnost", "natjecaj" }, allowSetters = true)
    private Set<Prijava> prijavas = new HashSet<>();

    @OneToMany(mappedBy = "natjecaj", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Zahtjev> zahtjevs = new HashSet<>();

    @JsonIgnoreProperties(value = { "natjecaj", "prijava" }, allowSetters = true)
    @OneToOne(mappedBy = "natjecaj")
    private Mobilnost mobilnost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Set<Zahtjev> getZahtjevs() {
        return zahtjevs;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class NatjecajHasPrijaveException extends RuntimeException {

        public NatjecajHasPrijaveException(String message) {
            super(message);
        }
    }

    @PreRemove
    private void preventDeleteIfHasPrijave() {
        if (!prijavas.isEmpty()) {
            throw new NatjecajHasPrijaveException("Nije moguće izbrisati natječaj jer postoje prijave.");
        }
    }

    public Set<Zahtjev> getZahtjevsByNatjecajId(Long natjecajId) {
        return zahtjevs.stream().filter(zahtjev -> zahtjev.getNatjecaj().getId().equals(natjecajId)).collect(Collectors.toSet());
    }

    public void setZahtjevs(Set<Zahtjev> zahtjevs) {
        this.zahtjevs = zahtjevs;
    }

    public Long getId() {
        return this.id;
    }

    public Natjecaj id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Natjecaj name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Natjecaj description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public Natjecaj createDate(Instant createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDatumOd() {
        return this.datumOd;
    }

    public Natjecaj datumOd(LocalDate datumOd) {
        this.setDatumOd(datumOd);
        return this;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return this.datumDo;
    }

    public Natjecaj datumDo(LocalDate datumDo) {
        this.setDatumDo(datumDo);
        return this;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public Status getStatus() {
        return this.status;
    }

    public Natjecaj status(Status status) {
        this.setStatus(status);
        return this;
    }

    public Korisnik getKorisnik() {
        return this.korisnik;
    }

    public Natjecaj korisnik(Korisnik korisnik) {
        this.setKorisnik(korisnik);
        return this;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Prijava> getPrijavas() {
        return this.prijavas;
    }

    public void setPrijavas(Set<Prijava> prijavas) {
        if (this.prijavas != null) {
            this.prijavas.forEach(i -> i.setNatjecaj(null));
        }
        if (prijavas != null) {
            prijavas.forEach(i -> i.setNatjecaj(this));
        }
        this.prijavas = prijavas;
    }

    public Natjecaj prijavas(Set<Prijava> prijavas) {
        this.setPrijavas(prijavas);
        return this;
    }

    public Natjecaj addPrijava(Prijava prijava) {
        this.prijavas.add(prijava);
        prijava.setNatjecaj(this);
        return this;
    }

    public Natjecaj removePrijava(Prijava prijava) {
        this.prijavas.remove(prijava);
        prijava.setNatjecaj(null);
        return this;
    }

    public Mobilnost getMobilnost() {
        return this.mobilnost;
    }

    public void setMobilnost(Mobilnost mobilnost) {
        if (this.mobilnost != null) {
            this.mobilnost.setNatjecaj(null);
        }
        if (mobilnost != null) {
            mobilnost.setNatjecaj(this);
        }
        this.mobilnost = mobilnost;
    }

    public Natjecaj mobilnost(Mobilnost mobilnost) {
        this.setMobilnost(mobilnost);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Natjecaj)) {
            return false;
        }
        return id != null && id.equals(((Natjecaj) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Natjecaj{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", datumOd='" + getDatumOd() + "'" +
            ", datumDo='" + getDatumDo() + "'" +
            ", status='" + getStatus() + "'" +
            ", korisnik='" + getKorisnik() + "'" +
            "}";
    }
}
