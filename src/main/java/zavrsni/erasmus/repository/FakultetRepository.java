package zavrsni.erasmus.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Fakultet;

/**
 * Spring Data JPA repository for the Fakultet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FakultetRepository extends JpaRepository<Fakultet, Long> {}
