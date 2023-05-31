package zavrsni.erasmus.service.mapper;

import javax.swing.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.service.dto.ZahtjevDTO;

@Mapper(componentModel = "spring")
public interface ZahtjevMapper extends EntityMapper<ZahtjevDTO, Zahtjev> {
    @Override
    @Mapping(target = "natjecaj", ignore = true)
    ZahtjevDTO toDto(Zahtjev entity);
}
