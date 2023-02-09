package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Fakultet;
import zavrsni.erasmus.service.dto.FakultetDTO;

/**
 * Mapper for the entity {@link Fakultet} and its DTO {@link FakultetDTO}.
 */
@Mapper(componentModel = "spring")
public interface FakultetMapper extends EntityMapper<FakultetDTO, Fakultet> {}
