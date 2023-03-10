package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class ZahtjevDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private NatjecajDTO natjecaj;

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

    public NatjecajDTO getNatjecaj() {
        return natjecaj;
    }

    public void setNatjecaj(NatjecajDTO natjecaj) {
        this.natjecaj = natjecaj;
    }

    @Override
    public String toString() {
        return "ZahtjevDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", natjecaj=" + getNatjecaj() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZahtjevDTO)) {
            return false;
        }

        ZahtjevDTO zahtjevDTO = (ZahtjevDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, zahtjevDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
