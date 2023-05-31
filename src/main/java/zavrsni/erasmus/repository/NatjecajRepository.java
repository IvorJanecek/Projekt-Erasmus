package zavrsni.erasmus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Natjecaj;

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

    @Query("select n from Natjecaj n where n.datumDo <= current_date and n.status = 'OTVOREN'")
    List<Natjecaj> findNatjecajiToClose();
}
