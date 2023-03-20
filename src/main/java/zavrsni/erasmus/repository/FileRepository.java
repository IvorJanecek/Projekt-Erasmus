package zavrsni.erasmus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zavrsni.erasmus.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {}
