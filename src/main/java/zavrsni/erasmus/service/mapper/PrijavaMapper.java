package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.*;
import zavrsni.erasmus.service.dto.*;

/**
 * Mapper for the entity {@link Prijava} and its DTO {@link PrijavaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrijavaMapper extends EntityMapper<PrijavaDTO, Prijava> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "fakultet", source = "fakultet", qualifiedByName = "fakultetName")
    @Mapping(target = "natjecaj", source = "natjecaj", qualifiedByName = "natjecajName")
    //@Mapping(target = "uploadFiles", source = "files", qualifiedByName = "uploadFileName")
    PrijavaDTO toDto(Prijava s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    /*@Named("uploadFileName")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileType", source = "fileType")
    @Mapping(target = "data", source = "data")
    UploadFileDTO toUploadFile(UploadFile uploadFile);*/

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
