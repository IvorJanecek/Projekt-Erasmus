package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.ZahtjevDTO;

/**
 * Mapper for the entity {@link Natjecaj} and its DTO {@link NatjecajDTO}.
 */
@Mapper(componentModel = "spring")
public interface NatjecajMapper extends EntityMapper<NatjecajDTO, Natjecaj> {
    @Override
    @Mapping(target = "zahtjev.natjecaj", ignore = true)
    NatjecajDTO toDto(Natjecaj natjecaj);

    default ZahtjevDTO zahtjevToZahtjevDTO(Zahtjev zahtjev) {
        if (zahtjev == null) {
            return null;
        }

        ZahtjevDTO zahtjevDTO = new ZahtjevDTO();

        zahtjevDTO.setId(zahtjev.getId());
        zahtjevDTO.setName(zahtjev.getName());
        zahtjevDTO.setNatjecaj(null);

        return zahtjevDTO;
    }
}
