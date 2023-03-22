package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import zavrsni.erasmus.domain.enumeration.Kategorija;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Prijava} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrijavaDTO implements Serializable {

    private Long id;

    @NotNull
    private String prijavaName;

    @NotNull
    private String opis;

    private Instant createdDate;

    private Boolean prihvacen;

    @NotNull
    private LocalDate trajanjeOd;

    @NotNull
    private LocalDate trajanjeDo;

    @Lob
    private byte[] data;

    private String dataContentType;
    private Kategorija kategorija;

    private UserDTO user;

    private FakultetDTO fakultet;

    private NatjecajDTO natjecaj;

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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getPrihvacen() {
        return prihvacen;
    }

    public void setPrihvacen(Boolean prihvacen) {
        this.prihvacen = prihvacen;
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
            ", opis='" + getOpis() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", prihvacen='" + getPrihvacen() + "'" +
            ", trajanjeOd='" + getTrajanjeOd() + "'" +
            ", trajanjeDo='" + getTrajanjeDo() + "'" +
            ", kategorija='" + getKategorija() + "'" +
            ", user=" + getUser() +
            ", fakultet=" + getFakultet() +
            ", natjecaj=" + getNatjecaj() +
            "}";
    }
}
