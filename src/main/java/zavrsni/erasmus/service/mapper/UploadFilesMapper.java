package zavrsni.erasmus.service.mapper;

import org.mapstruct.Mapper;
import zavrsni.erasmus.domain.UploadFile;
import zavrsni.erasmus.service.dto.UploadFileDTO;

@Mapper(componentModel = "spring")
public interface UploadFilesMapper extends EntityMapper<UploadFileDTO, UploadFile> {}
