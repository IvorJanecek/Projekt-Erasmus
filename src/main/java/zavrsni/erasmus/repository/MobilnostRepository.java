package zavrsni.erasmus.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.Prijava;

/**
 * Spring Data JPA repository for the Mobilnost entity.
 */
@Repository
public interface MobilnostRepository extends JpaRepository<Mobilnost, Long> {
    default Optional<Mobilnost> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    @Query(
        value = "SELECT mobilnost FROM Mobilnost mobilnost WHERE (:#{hasRole('ROLE_ADMIN')} = true OR mobilnost.user.login = ?#{principal.username})",
        countQuery = "SELECT COUNT(DISTINCT mobilnost) FROM Mobilnost mobilnost WHERE (:#{hasRole('ROLE_ADMIN')} = true OR mobilnost.user.login = ?#{principal.username})"
    )
    Page<Mobilnost> findByUserIsCurrentUserOrAdmin(Pageable pageable);

    default Page<Mobilnost> findAllWithEagerRelationshipsForCurrentUser(Pageable pageable) {
        return this.findAllWithToOneRelationshipsForCurrentUser(pageable);
    }

    @Query(
        value = "select distinct mobilnost from Mobilnost mobilnost left join fetch mobilnost.prijava left join fetch mobilnost.natjecaj where (:#{hasRole('ROLE_ADMIN')} = true OR mobilnost.user.login = ?#{principal.username})",
        countQuery = "select count(distinct mobilnost) from Mobilnost mobilnost WHERE (:#{hasRole('ROLE_ADMIN')} = true OR mobilnost.user.login = ?#{principal.username})"
    )
    Page<Mobilnost> findAllWithToOneRelationshipsForCurrentUser(Pageable pageable);

    default List<Mobilnost> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Mobilnost> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct mobilnost from Mobilnost mobilnost left join fetch mobilnost.natjecaj left join fetch mobilnost.prijava",
        countQuery = "select count(distinct mobilnost) from Mobilnost mobilnost"
    )
    Page<Mobilnost> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct mobilnost from Mobilnost mobilnost left join fetch mobilnost.natjecaj left join fetch mobilnost.prijava")
    List<Mobilnost> findAllWithToOneRelationships();

    @Query(
        "select mobilnost from Mobilnost mobilnost left join fetch mobilnost.natjecaj left join fetch mobilnost.prijava where mobilnost.id =:id"
    )
    Optional<Mobilnost> findOneWithToOneRelationships(@Param("id") Long id);
}
