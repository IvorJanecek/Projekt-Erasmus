package zavrsni.erasmus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Zahtjev;

@Repository
public interface ZahtjevRepository extends JpaRepository<Zahtjev, Long> {
    List<Zahtjev> findAllByNatjecajId(Long id);
}
