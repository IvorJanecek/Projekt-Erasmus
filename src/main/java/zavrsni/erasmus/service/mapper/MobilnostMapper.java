package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;

/**
 * Mapper for the entity {@link Mobilnost} and its DTO {@link MobilnostDTO}.
 */
@Mapper(componentModel = "spring")
public interface MobilnostMapper extends EntityMapper<MobilnostDTO, Mobilnost> {
    @Mapping(target = "natjecaj", source = "natjecaj", qualifiedByName = "natjecajName")
    @Mapping(target = "prijava", source = "prijava", qualifiedByName = "prijavaPrijavaName")
    MobilnostDTO toDto(Mobilnost s);

    @Named("natjecajName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    NatjecajDTO toDtoNatjecajName(Natjecaj natjecaj);

    @Named("prijavaPrijavaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "prijavaName", source = "prijavaName")
    PrijavaDTO toDtoPrijavaPrijavaName(Prijava prijava);
}
