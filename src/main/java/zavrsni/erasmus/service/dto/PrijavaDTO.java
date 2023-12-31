package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import zavrsni.erasmus.domain.enumeration.Kategorija;
import zavrsni.erasmus.domain.enumeration.StatusPrijave;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Prijava} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrijavaDTO implements Serializable {

    private Long id;

    @NotNull
    private String prijavaName;

    private Instant createdDate;

    @NotNull
    private LocalDate trajanjeOd;

    @NotNull
    private LocalDate trajanjeDo;

    @Lob
    private byte[] data;

    private String dataContentType;
    private Kategorija kategorija;

    private StatusPrijave statusPrijave;

    private UserDTO user;

    private FakultetDTO fakultet;

    private NatjecajDTO natjecaj;

    private List<UploadFileDTO> uploadFiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrijavaName() {
        return prijavaName;
    }

    public void setPrijavaName(String prijavaName) {
        this.prijavaName = prijavaName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getTrajanjeOd() {
        return trajanjeOd;
    }

    public void setTrajanjeOd(LocalDate trajanjeOd) {
        this.trajanjeOd = trajanjeOd;
    }

    public LocalDate getTrajanjeDo() {
        return trajanjeDo;
    }

    public void setTrajanjeDo(LocalDate trajanjeDo) {
        this.trajanjeDo = trajanjeDo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public StatusPrijave getStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(StatusPrijave statusPrijave) {
        this.statusPrijave = statusPrijave;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public FakultetDTO getFakultet() {
        return fakultet;
    }

    public void setFakultet(FakultetDTO fakultet) {
        this.fakultet = fakultet;
    }

    public NatjecajDTO getNatjecaj() {
        return natjecaj;
    }

    public void setNatjecaj(NatjecajDTO natjecaj) {
        this.natjecaj = natjecaj;
    }

    public List<UploadFileDTO> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadFileDTO> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrijavaDTO)) {
            return false;
        }

        PrijavaDTO prijavaDTO = (PrijavaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prijavaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrijavaDTO{" +
            "id=" + getId() +
            ", prijavaName='" + getPrijavaName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", trajanjeOd='" + getTrajanjeOd() + "'" +
            ", trajanjeDo='" + getTrajanjeDo() + "'" +
            ", kategorija='" + getKategorija() + "'" +
            ", user=" + getUser() +
            ", fakultet=" + getFakultet() +
            ", natjecaj=" + getNatjecaj() +
            ", statusPrijave=" + getStatusPrijave() +
            "}";
    }
}
