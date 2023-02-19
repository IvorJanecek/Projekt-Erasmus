package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Fakultet;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.User;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Prijava} and its DTO {@link PrijavaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrijavaMapper extends EntityMapper<PrijavaDTO, Prijava> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "fakultet", source = "fakultet", qualifiedByName = "fakultetName")
    @Mapping(target = "natjecaj", source = "natjecaj", qualifiedByName = "natjecajName")
    PrijavaDTO toDto(Prijava s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    @Named("fakultetName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FakultetDTO toDtoFakultetName(Fakultet fakultet);

    @Named("natjecajName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    NatjecajDTO toDtoNatjecajName(Natjecaj natjecaj);
}
