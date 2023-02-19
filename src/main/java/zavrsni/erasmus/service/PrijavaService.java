package zavrsni.erasmus.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.User;
import zavrsni.erasmus.repository.PrijavaRepository;
import zavrsni.erasmus.service.dto.PrijavaDTO;

/**
 * Service Interface for managing {@link zavrsni.erasmus.domain.Prijava}.
 */
public interface PrijavaService {
    /**
     * Save a prijava.
     *
     * @param prijavaDTO the entity to save.
     * @return the persisted entity.
     */
    PrijavaDTO save(PrijavaDTO prijavaDTO);

    /**
     * Updates a prijava.
     *
     * @param prijavaDTO the entity to update.
     * @return the persisted entity.
     */
    PrijavaDTO update(PrijavaDTO prijavaDTO);

    /**
     * Partially updates a prijava.
     *
     * @param prijavaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrijavaDTO> partialUpdate(PrijavaDTO prijavaDTO);

    /**
     * Get all the prijavas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public default boolean hasUserApplied(Natjecaj natjecaj, User user) {
        PrijavaRepository prijavaRepository = null;
        Optional<Prijava> prijavaOptional = prijavaRepository.findByNatjecajAndUser(natjecaj, user);
        return prijavaOptional.isPresent();
    }

    Page<PrijavaDTO> findAll(Pageable pageable);
    /**
     * Get all the PrijavaDTO where Mobilnost is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PrijavaDTO> findAllWhereMobilnostIsNull();

    /**
     * Get all the prijavas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrijavaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" prijava.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrijavaDTO> findOne(Long id);

    /**
     * Delete the "id" prijava.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
