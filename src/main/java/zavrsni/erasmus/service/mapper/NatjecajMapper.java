package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.service.dto.NatjecajDTO;

/**
 * Mapper for the entity {@link Natjecaj} and its DTO {@link NatjecajDTO}.
 */
@Mapper(componentModel = "spring")
public interface NatjecajMapper extends EntityMapper<NatjecajDTO, Natjecaj> {}
