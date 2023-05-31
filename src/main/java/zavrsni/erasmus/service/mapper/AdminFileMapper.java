package zavrsni.erasmus.service.mapper;

import org.mapstruct.Mapper;
import zavrsni.erasmus.domain.UploadFileAdmin;
import zavrsni.erasmus.service.dto.AdminFileDTO;

@Mapper(componentModel = "spring")
public interface AdminFileMapper extends EntityMapper<AdminFileDTO, UploadFileAdmin> {}
