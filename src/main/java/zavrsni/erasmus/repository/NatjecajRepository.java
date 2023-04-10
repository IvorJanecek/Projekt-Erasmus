package zavrsni.erasmus.repository;

import java.util.List;
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
public interface NatjecajRepository extends JpaRepository<Natjecaj, Long> {
    @Query(
        "select e from Natjecaj e where " +
        "(:#{hasRole('ROLE_ADMIN')} = true or " +
        "(:#{hasRole('ROLE_PROFESOR')} = true and e.korisnik = 'PROFESOR') or " +
        "(:#{hasRole('ROLE_USER')} = true and e.korisnik = 'USER'))"
    )
    List<Natjecaj> findAllFilteredByUserRoleAndEntityType();
}
