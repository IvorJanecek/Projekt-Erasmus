package zavrsni.erasmus.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Prijava;

/**
 * Spring Data JPA repository for the Prijava entity.
 */
@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {
    @Query("select prijava from Prijava prijava where prijava.user.login = ?#{principal.username}")
    List<Prijava> findByUserIsCurrentUser();

    default Optional<Prijava> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Prijava> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Prijava> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct prijava from Prijava prijava left join fetch prijava.fakultet left join fetch prijava.natjecaj",
        countQuery = "select count(distinct prijava) from Prijava prijava"
    )
    Page<Prijava> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct prijava from Prijava prijava left join fetch prijava.fakultet left join fetch prijava.natjecaj")
    List<Prijava> findAllWithToOneRelationships();

    @Query("select prijava from Prijava prijava left join fetch prijava.fakultet left join fetch prijava.natjecaj where prijava.id =:id")
    Optional<Prijava> findOneWithToOneRelationships(@Param("id") Long id);
}
