package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link zavrsni.erasmus.domain.Fakultet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FakultetDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String zemlja;

    @NotNull
    private String grad;

    @NotNull
    private String adresa;

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

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FakultetDTO)) {
            return false;
        }

        FakultetDTO fakultetDTO = (FakultetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fakultetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FakultetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zemlja='" + getZemlja() + "'" +
            ", grad='" + getGrad() + "'" +
            ", adresa='" + getAdresa() + "'" +
            "}";
    }
}
