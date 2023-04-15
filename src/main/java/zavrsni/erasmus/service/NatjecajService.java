package zavrsni.erasmus.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zavrsni.erasmus.service.dto.NatjecajDTO;

/**
 * Service Interface for managing {@link zavrsni.erasmus.domain.Natjecaj}.
 */
public interface NatjecajService {
    /**
     * Save a natjecaj.
     *
     * @param natjecajDTO the entity to save.
     * @return the persisted entity.
     */
    NatjecajDTO save(NatjecajDTO natjecajDTO);

    /**
     * Updates a natjecaj.
     *
     * @param natjecajDTO the entity to update.
     * @return the persisted entity.
     */
    NatjecajDTO update(NatjecajDTO natjecajDTO);

    /**
     * Partially updates a natjecaj.
     *
     * @param natjecajDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatjecajDTO> partialUpdate(NatjecajDTO natjecajDTO);

    /**
     * Get all the natjecajs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatjecajDTO> findAll(Pageable pageable);
    /**
     * Get all the NatjecajDTO where Mobilnost is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<NatjecajDTO> findAllWhereMobilnostIsNull();

    /**
     * Get the "id" natjecaj.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    List<NatjecajDTO> findAllFilteredByUserRoleAndEntityType();
    Optional<NatjecajDTO> findOne(Long id);

    /**
     * Delete the "id" natjecaj.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void closeExpiredNatjecaji();
}
