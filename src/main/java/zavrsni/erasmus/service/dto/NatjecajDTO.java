package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.*;
import zavrsni.erasmus.domain.enumeration.Korisnik;
import zavrsni.erasmus.domain.enumeration.Status;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Natjecaj} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NatjecajDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Instant createDate;

    private LocalDate datumOd;

    private LocalDate datumDo;

    private Status status;

    private Korisnik korisnik;
    private List<ZahtjevDTO> zahtjevs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZahtjevs(List<ZahtjevDTO> zahtjevs) {
        this.zahtjevs = zahtjevs;
    }

    public List<ZahtjevDTO> getZahtjevs() {
        return zahtjevs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatjecajDTO)) {
            return false;
        }

        NatjecajDTO natjecajDTO = (NatjecajDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, natjecajDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatjecajDTO{" +
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
