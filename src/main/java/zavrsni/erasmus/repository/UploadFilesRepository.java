package zavrsni.erasmus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.UploadFile;

@Repository
public interface UploadFilesRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByMobilnost(Mobilnost mobilnost);
}
