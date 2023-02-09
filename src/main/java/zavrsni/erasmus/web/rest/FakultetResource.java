package zavrsni.erasmus.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import zavrsni.erasmus.repository.FakultetRepository;
import zavrsni.erasmus.service.FakultetService;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zavrsni.erasmus.domain.Fakultet}.
 */
@RestController
@RequestMapping("/api")
public class FakultetResource {

    private final Logger log = LoggerFactory.getLogger(FakultetResource.class);

    private static final String ENTITY_NAME = "fakultet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FakultetService fakultetService;

    private final FakultetRepository fakultetRepository;

    public FakultetResource(FakultetService fakultetService, FakultetRepository fakultetRepository) {
        this.fakultetService = fakultetService;
        this.fakultetRepository = fakultetRepository;
    }

    /**
     * {@code POST  /fakultets} : Create a new fakultet.
     *
     * @param fakultetDTO the fakultetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fakultetDTO, or with status {@code 400 (Bad Request)} if the fakultet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fakultets")
    public ResponseEntity<FakultetDTO> createFakultet(@Valid @RequestBody FakultetDTO fakultetDTO) throws URISyntaxException {
        log.debug("REST request to save Fakultet : {}", fakultetDTO);
        if (fakultetDTO.getId() != null) {
            throw new BadRequestAlertException("A new fakultet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FakultetDTO result = fakultetService.save(fakultetDTO);
        return ResponseEntity
            .created(new URI("/api/fakultets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fakultets/:id} : Updates an existing fakultet.
     *
     * @param id the id of the fakultetDTO to save.
     * @param fakultetDTO the fakultetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fakultetDTO,
     * or with status {@code 400 (Bad Request)} if the fakultetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fakultetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fakultets/{id}")
    public ResponseEntity<FakultetDTO> updateFakultet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FakultetDTO fakultetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fakultet : {}, {}", id, fakultetDTO);
        if (fakultetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fakultetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fakultetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FakultetDTO result = fakultetService.update(fakultetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fakultetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fakultets/:id} : Partial updates given fields of an existing fakultet, field will ignore if it is null
     *
     * @param id the id of the fakultetDTO to save.
     * @param fakultetDTO the fakultetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fakultetDTO,
     * or with status {@code 400 (Bad Request)} if the fakultetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fakultetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fakultetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fakultets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FakultetDTO> partialUpdateFakultet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FakultetDTO fakultetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fakultet partially : {}, {}", id, fakultetDTO);
        if (fakultetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fakultetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fakultetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FakultetDTO> result = fakultetService.partialUpdate(fakultetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fakultetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fakultets} : get all the fakultets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fakultets in body.
     */
    @GetMapping("/fakultets")
    public List<FakultetDTO> getAllFakultets() {
        log.debug("REST request to get all Fakultets");
        return fakultetService.findAll();
    }

    /**
     * {@code GET  /fakultets/:id} : get the "id" fakultet.
     *
     * @param id the id of the fakultetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fakultetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fakultets/{id}")
    public ResponseEntity<FakultetDTO> getFakultet(@PathVariable Long id) {
        log.debug("REST request to get Fakultet : {}", id);
        Optional<FakultetDTO> fakultetDTO = fakultetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fakultetDTO);
    }

    /**
     * {@code DELETE  /fakultets/:id} : delete the "id" fakultet.
     *
     * @param id the id of the fakultetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fakultets/{id}")
    public ResponseEntity<Void> deleteFakultet(@PathVariable Long id) {
        log.debug("REST request to delete Fakultet : {}", id);
        fakultetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
