package zavrsni.erasmus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.UploadFileAdmin;

public interface UploadFilesAdminRepository extends JpaRepository<UploadFileAdmin, Long> {
    List<UploadFileAdmin> findByMobilnost(Mobilnost mobilnost);
}
