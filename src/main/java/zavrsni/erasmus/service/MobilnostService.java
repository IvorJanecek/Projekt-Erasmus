package zavrsni.erasmus.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;

/**
 * Service Interface for managing {@link zavrsni.erasmus.domain.Mobilnost}.
 */
public interface MobilnostService {
    /**
     * Save a mobilnost.
     *
     * @param mobilnostDTO the entity to save.
     * @return the persisted entity.
     */
    MobilnostDTO save(MobilnostDTO mobilnostDTO);

    /**
     * Updates a mobilnost.
     *
     * @param mobilnostDTO the entity to update.
     * @return the persisted entity.
     */
    MobilnostDTO update(MobilnostDTO mobilnostDTO);

    /**
     * Partially updates a mobilnost.
     *
     * @param mobilnostDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MobilnostDTO> partialUpdate(MobilnostDTO mobilnostDTO);

    /**
     * Get all the mobilnosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MobilnostDTO> findAll(Pageable pageable);

    /**
     * Get all the mobilnosts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MobilnostDTO> findAllWithEagerRelationships(Pageable pageable);

    Page<MobilnostDTO> findByUserIsCurrentUser(Pageable pageable);

    Page<MobilnostDTO> findByUserIsCurrentUserWithEagerRelationships(Pageable pageable);
    /**
     * Get the "id" mobilnost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobilnostDTO> findOne(Long id);

    /**
     * Delete the "id" mobilnost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
