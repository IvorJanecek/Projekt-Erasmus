package zavrsni.erasmus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import zavrsni.erasmus.domain.enumeration.StatusMobilnosti;
import zavrsni.erasmus.domain.enumeration.StatusPrijave;

/**
 * A Mobilnost.
 */
@Entity
@Table(name = "mobilnost")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mobilnost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "mobilnost_name", nullable = false)
    private String mobilnostName;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_date")
    private Instant createdDate;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @JsonIgnoreProperties(value = { "prijavas", "mobilnost" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Natjecaj natjecaj;

    @JsonIgnoreProperties(value = { "user", "fakultet", "mobilnost", "natjecaj" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Prijava prijava;

    @OneToMany(mappedBy = "mobilnost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UploadFile> files = new ArrayList<>();

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusMobilnosti statusMobilnosti;

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

    public Mobilnost id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mobilnost user(User user) {
        this.setUser(user);
        return this;
    }

    public String getMobilnostName() {
        return this.mobilnostName;
    }

    public Mobilnost mobilnostName(String mobilnostName) {
        this.setMobilnostName(mobilnostName);
        return this;
    }

    public void setMobilnostName(String mobilnostName) {
        this.mobilnostName = mobilnostName;
    }

    public String getDescription() {
        return this.description;
    }

    public Mobilnost description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusMobilnosti getStatusMobilnosti() {
        return this.statusMobilnosti;
    }

    public Mobilnost statusMobilnosti(StatusMobilnosti statusMobilnosti) {
        this.setStatusMobilnosti(statusMobilnosti);
        return this;
    }

    public void setStatusMobilnosti(StatusMobilnosti statusMobilnosti) {
        this.statusMobilnosti = statusMobilnosti;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Mobilnost createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getData() {
        return this.data;
    }

    public Mobilnost data(byte[] data) {
        this.setData(data);
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return this.dataContentType;
    }

    public Mobilnost dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Natjecaj getNatjecaj() {
        return this.natjecaj;
    }

    public void setNatjecaj(Natjecaj natjecaj) {
        this.natjecaj = natjecaj;
    }

    public Mobilnost natjecaj(Natjecaj natjecaj) {
        this.setNatjecaj(natjecaj);
        return this;
    }

    public Prijava getPrijava() {
        return this.prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }

    public Mobilnost prijava(Prijava prijava) {
        this.setPrijava(prijava);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mobilnost)) {
            return false;
        }
        return id != null && id.equals(((Mobilnost) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mobilnost{" +
            "id=" + getId() +
            ", mobilnostName='" + getMobilnostName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            ", statusMobilnosti='" + getStatusMobilnosti() + "'" +
            "}";
    }
}
