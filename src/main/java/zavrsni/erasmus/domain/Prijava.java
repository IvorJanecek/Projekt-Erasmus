package zavrsni.erasmus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.mapstruct.EnumMapping;
import zavrsni.erasmus.domain.enumeration.Kategorija;
import zavrsni.erasmus.domain.enumeration.StatusPrijave;

/**
 * A Prijava.
 */
@Entity
@Table(name = "prijava")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prijava implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "prijava_name", nullable = false)
    private String prijavaName;

    @NotNull
    @Column(name = "opis", nullable = false)
    private String opis;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "prihvacen")
    private Boolean prihvacen;

    @NotNull
    @Column(name = "trajanje_od", nullable = false)
    private LocalDate trajanjeOd;

    @NotNull
    @Column(name = "trajanje_do", nullable = false)
    private LocalDate trajanjeDo;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategorija")
    private Kategorija kategorija;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusPrijave statusPrijave;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prijavas" }, allowSetters = true)
    private Fakultet fakultet;

    @JsonIgnoreProperties(value = { "natjecaj", "prijava" }, allowSetters = true)
    @OneToOne(mappedBy = "prijava")
    private Mobilnost mobilnost;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prijavas", "mobilnost" }, allowSetters = true)
    private Natjecaj natjecaj;

    @OneToMany(mappedBy = "prijava", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UploadFile> files = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public List<UploadFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }

    public Long getId() {
        return this.id;
    }

    public Prijava id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrijavaName() {
        return this.prijavaName;
    }

    public Prijava prijavaName(String prijavaName) {
        this.setPrijavaName(prijavaName);
        return this;
    }

    public void setPrijavaName(String prijavaName) {
        this.prijavaName = prijavaName;
    }

    public String getOpis() {
        return this.opis;
    }

    public Prijava opis(String opis) {
        this.setOpis(opis);
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Prijava createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getPrihvacen() {
        return this.prihvacen;
    }

    public Prijava prihvacen(Boolean prihvacen) {
        this.setPrihvacen(prihvacen);
        return this;
    }

    public void setPrihvacen(Boolean prihvacen) {
        this.prihvacen = prihvacen;
    }

    public LocalDate getTrajanjeOd() {
        return this.trajanjeOd;
    }

    public Prijava trajanjeOd(LocalDate trajanjeOd) {
        this.setTrajanjeOd(trajanjeOd);
        return this;
    }

    public void setTrajanjeOd(LocalDate trajanjeOd) {
        this.trajanjeOd = trajanjeOd;
    }

    public LocalDate getTrajanjeDo() {
        return this.trajanjeDo;
    }

    public Prijava trajanjeDo(LocalDate trajanjeDo) {
        this.setTrajanjeDo(trajanjeDo);
        return this;
    }

    public void setTrajanjeDo(LocalDate trajanjeDo) {
        this.trajanjeDo = trajanjeDo;
    }

    public byte[] getData() {
        return this.data;
    }

    public Prijava data(byte[] data) {
        this.setData(data);
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return this.dataContentType;
    }

    public Prijava dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Kategorija getKategorija() {
        return this.kategorija;
    }

    public Prijava kategorija(Kategorija kategorija) {
        this.setKategorija(kategorija);
        return this;
    }

    public StatusPrijave getStatusPrijave() {
        return this.statusPrijave;
    }

    public Prijava statusPrijave(StatusPrijave statusPrijave) {
        this.setStatusPrijave(statusPrijave);
        return this;
    }

    public void setStatusPrijave(StatusPrijave statusPrijave) {
        this.statusPrijave = statusPrijave;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Prijava user(User user) {
        this.setUser(user);
        return this;
    }

    public Fakultet getFakultet() {
        return this.fakultet;
    }

    public void setFakultet(Fakultet fakultet) {
        this.fakultet = fakultet;
    }

    public Prijava fakultet(Fakultet fakultet) {
        this.setFakultet(fakultet);
        return this;
    }

    public Mobilnost getMobilnost() {
        return this.mobilnost;
    }

    public void setMobilnost(Mobilnost mobilnost) {
        if (this.mobilnost != null) {
            this.mobilnost.setPrijava(null);
        }
        if (mobilnost != null) {
            mobilnost.setPrijava(this);
        }
        this.mobilnost = mobilnost;
    }

    public Prijava mobilnost(Mobilnost mobilnost) {
        this.setMobilnost(mobilnost);
        return this;
    }

    public Natjecaj getNatjecaj() {
        return this.natjecaj;
    }

    public void setNatjecaj(Natjecaj natjecaj) {
        this.natjecaj = natjecaj;
    }

    public Prijava natjecaj(Natjecaj natjecaj) {
        this.setNatjecaj(natjecaj);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prijava)) {
            return false;
        }
        return id != null && id.equals(((Prijava) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prijava{" +
            "id=" + getId() +
            ", prijavaName='" + getPrijavaName() + "'" +
            ", opis='" + getOpis() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", prihvacen='" + getPrihvacen() + "'" +
            ", trajanjeOd='" + getTrajanjeOd() + "'" +
            ", trajanjeDo='" + getTrajanjeDo() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            ", kategorija='" + getKategorija() + "'" +
            ", statusPrijave'" + getStatusPrijave() + "'" +
            "}";
    }
}
