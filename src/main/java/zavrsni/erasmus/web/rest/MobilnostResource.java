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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zavrsni.erasmus.repository.MobilnostRepository;
import zavrsni.erasmus.service.MobilnostService;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zavrsni.erasmus.domain.Mobilnost}.
 */
@RestController
@RequestMapping("/api")
public class MobilnostResource {

    private final Logger log = LoggerFactory.getLogger(MobilnostResource.class);

    private static final String ENTITY_NAME = "mobilnost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MobilnostService mobilnostService;

    private final MobilnostRepository mobilnostRepository;

    public MobilnostResource(MobilnostService mobilnostService, MobilnostRepository mobilnostRepository) {
        this.mobilnostService = mobilnostService;
        this.mobilnostRepository = mobilnostRepository;
    }

    /**
     * {@code POST  /mobilnosts} : Create a new mobilnost.
     *
     * @param mobilnostDTO the mobilnostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobilnostDTO, or with status {@code 400 (Bad Request)} if the mobilnost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mobilnosts")
    public ResponseEntity<MobilnostDTO> createMobilnost(@Valid @RequestBody MobilnostDTO mobilnostDTO) throws URISyntaxException {
        log.debug("REST request to save Mobilnost : {}", mobilnostDTO);
        if (mobilnostDTO.getId() != null) {
            throw new BadRequestAlertException("A new mobilnost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MobilnostDTO result = mobilnostService.save(mobilnostDTO);
        return ResponseEntity
            .created(new URI("/api/mobilnosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mobilnosts/:id} : Updates an existing mobilnost.
     *
     * @param id the id of the mobilnostDTO to save.
     * @param mobilnostDTO the mobilnostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobilnostDTO,
     * or with status {@code 400 (Bad Request)} if the mobilnostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobilnostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mobilnosts/{id}")
    public ResponseEntity<MobilnostDTO> updateMobilnost(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MobilnostDTO mobilnostDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Mobilnost : {}, {}", id, mobilnostDTO);
        if (mobilnostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mobilnostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mobilnostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MobilnostDTO result = mobilnostService.update(mobilnostDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mobilnostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mobilnosts/:id} : Partial updates given fields of an existing mobilnost, field will ignore if it is null
     *
     * @param id the id of the mobilnostDTO to save.
     * @param mobilnostDTO the mobilnostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobilnostDTO,
     * or with status {@code 400 (Bad Request)} if the mobilnostDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mobilnostDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mobilnostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mobilnosts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MobilnostDTO> partialUpdateMobilnost(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MobilnostDTO mobilnostDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mobilnost partially : {}, {}", id, mobilnostDTO);
        if (mobilnostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mobilnostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mobilnostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MobilnostDTO> result = mobilnostService.partialUpdate(mobilnostDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mobilnostDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mobilnosts} : get all the mobilnosts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobilnosts in body.
     */
    @GetMapping("/mobilnosts")
    public ResponseEntity<List<MobilnostDTO>> getAllMobilnosts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Mobilnosts");
        Page<MobilnostDTO> page;
        if (eagerload) {
            page = mobilnostService.findAllWithEagerRelationships(pageable);
        } else {
            page = mobilnostService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mobilnosts/:id} : get the "id" mobilnost.
     *
     * @param id the id of the mobilnostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobilnostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mobilnosts/{id}")
    public ResponseEntity<MobilnostDTO> getMobilnost(@PathVariable Long id) {
        log.debug("REST request to get Mobilnost : {}", id);
        Optional<MobilnostDTO> mobilnostDTO = mobilnostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mobilnostDTO);
    }

    /**
     * {@code DELETE  /mobilnosts/:id} : delete the "id" mobilnost.
     *
     * @param id the id of the mobilnostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mobilnosts/{id}")
    public ResponseEntity<Void> deleteMobilnost(@PathVariable Long id) {
        log.debug("REST request to delete Mobilnost : {}", id);
        mobilnostService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
