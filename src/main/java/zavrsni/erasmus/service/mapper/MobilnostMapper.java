package zavrsni.erasmus.service.mapper;

import org.mapstruct.*;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.UploadFile;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.dto.UploadFileDTO;

/**
 * Mapper for the entity {@link Mobilnost} and its DTO {@link MobilnostDTO}.
 */
@Mapper(componentModel = "spring")
public interface MobilnostMapper extends EntityMapper<MobilnostDTO, Mobilnost> {
    @Mapping(target = "natjecaj", source = "natjecaj", qualifiedByName = "natjecajName")
    @Mapping(target = "prijava", source = "prijava", qualifiedByName = "prijavaPrijavaName")
    @Mapping(target = "uploadFiles", source = "files", qualifiedByName = "uploadFileName")
    MobilnostDTO toDto(Mobilnost s);

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
