package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import zavrsni.erasmus.domain.enumeration.StatusMobilnosti;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Mobilnost} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MobilnostDTO implements Serializable {

    private Long id;

    @NotNull
    private String mobilnostName;

    private Instant createdDate;

    @Lob
    private byte[] data;

    private String dataContentType;

    @NotNull
    private LocalDate trajanjeOd;

    @NotNull
    private LocalDate trajanjeDo;

    private NatjecajDTO natjecaj;

    private PrijavaDTO prijava;

    private UserDTO user;

    private StatusMobilnosti statusMobilnosti;

    private List<UploadFileDTO> uploadFiles;

    private List<AdminFileDTO> uploadFilesAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getMobilnostName() {
        return mobilnostName;
    }

    public void setMobilnostName(String mobilnostName) {
        this.mobilnostName = mobilnostName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public NatjecajDTO getNatjecaj() {
        return natjecaj;
    }

    public void setNatjecaj(NatjecajDTO natjecaj) {
        this.natjecaj = natjecaj;
    }

    public PrijavaDTO getPrijava() {
        return prijava;
    }

    public void setPrijava(PrijavaDTO prijava) {
        this.prijava = prijava;
    }

    public List<UploadFileDTO> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadFileDTO> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public List<AdminFileDTO> getUploadFilesAdmin() {
        return uploadFilesAdmin;
    }

    public void setUploadFilesAdmin(List<AdminFileDTO> uploadFilesAdmin) {
        this.uploadFilesAdmin = uploadFilesAdmin;
    }

    public StatusMobilnosti getStatusMobilnosti() {
        return statusMobilnosti;
    }

    public void setStatusMobilnosti(StatusMobilnosti statusMobilnosti) {
        this.statusMobilnosti = statusMobilnosti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobilnostDTO)) {
            return false;
        }

        MobilnostDTO mobilnostDTO = (MobilnostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mobilnostDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MobilnostDTO{" +
            "id=" + getId() +
            ", mobilnostName='" + getMobilnostName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", data='" + getData() + "'" +
            ", user=" + getUser() +
            ", trajanjeOd='" + getTrajanjeOd() + "'" +
            ", trajanjeDo='" + getTrajanjeDo() + "'" +
            ", natjecaj=" + getNatjecaj() +
            ", prijava=" + getPrijava() +
            ", statusMobilnosti=" + getStatusMobilnosti() +
             "}";
    }
}
