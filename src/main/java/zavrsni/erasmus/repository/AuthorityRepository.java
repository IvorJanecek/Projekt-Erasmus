package zavrsni.erasmus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zavrsni.erasmus.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
