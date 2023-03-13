package zavrsni.erasmus.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Zahtjev;

/**
 * Spring Data JPA repository for the Natjecaj entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatjecajRepository extends JpaRepository<Natjecaj, Long> {}
