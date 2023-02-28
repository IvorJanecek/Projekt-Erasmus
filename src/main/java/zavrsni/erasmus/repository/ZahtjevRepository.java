package zavrsni.erasmus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zavrsni.erasmus.domain.Zahtjev;

@Repository
public interface ZahtjevRepository extends JpaRepository<Zahtjev, Long> {}
