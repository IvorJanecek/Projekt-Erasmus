package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.*;
import zavrsni.erasmus.service.dto.*;

/**
 * Mapper for the entity {@link Mobilnost} and its DTO {@link MobilnostDTO}.
 */
@Mapper(componentModel = "spring")
public interface MobilnostMapper extends EntityMapper<MobilnostDTO, Mobilnost> {
    @Mapping(target = "natjecaj", source = "natjecaj", qualifiedByName = "natjecajName")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "prijava", source = "prijava", qualifiedByName = "prijavaPrijavaName")
    @Mapping(target = "uploadFiles", source = "files", qualifiedByName = "uploadFileName")
    MobilnostDTO toDto(Mobilnost s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    @Named("natjecajName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    NatjecajDTO toDtoNatjecajName(Natjecaj natjecaj);

    @Named("uploadFileName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileType", source = "fileType")
    @Mapping(target = "data", source = "data")
    UploadFileDTO toUploadFile(UploadFile uploadFile);

    @Named("prijavaPrijavaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "prijavaName", source = "prijavaName")
    PrijavaDTO toDtoPrijavaPrijavaName(Prijava prijava);
}
