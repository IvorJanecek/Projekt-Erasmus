package zavrsni.erasmus.service;

import java.util.List;
import java.util.Optional;
import zavrsni.erasmus.service.dto.FakultetDTO;

/**
 * Service Interface for managing {@link zavrsni.erasmus.domain.Fakultet}.
 */
public interface FakultetService {
    /**
     * Save a fakultet.
     *
     * @param fakultetDTO the entity to save.
     * @return the persisted entity.
     */
    FakultetDTO save(FakultetDTO fakultetDTO);

    /**
     * Updates a fakultet.
     *
     * @param fakultetDTO the entity to update.
     * @return the persisted entity.
     */
    FakultetDTO update(FakultetDTO fakultetDTO);

    /**
     * Partially updates a fakultet.
     *
     * @param fakultetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FakultetDTO> partialUpdate(FakultetDTO fakultetDTO);

    /**
     * Get all the fakultets.
     *
     * @return the list of entities.
     */
    List<FakultetDTO> findAll();

    /**
     * Get the "id" fakultet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FakultetDTO> findOne(Long id);

    /**
     * Delete the "id" fakultet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
