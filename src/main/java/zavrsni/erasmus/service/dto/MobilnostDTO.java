package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Mobilnost} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MobilnostDTO implements Serializable {

    private Long id;

    @NotNull
    private String mobilnostName;

    @NotNull
    private String description;

    private Instant createdDate;

    @Lob
    private byte[] data;

    private String dataContentType;
    private NatjecajDTO natjecaj;

    private PrijavaDTO prijava;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilnostName() {
        return mobilnostName;
    }

    public void setMobilnostName(String mobilnostName) {
        this.mobilnostName = mobilnostName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", data='" + getData() + "'" +
            ", natjecaj=" + getNatjecaj() +
            ", prijava=" + getPrijava() +
            "}";
    }
}
