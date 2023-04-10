package zavrsni.erasmus.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.repository.NatjecajRepository;
import zavrsni.erasmus.service.NatjecajService;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zavrsni.erasmus.domain.Natjecaj}.
 */
@RestController
@RequestMapping("/api")
public class NatjecajResource {

    private final Logger log = LoggerFactory.getLogger(NatjecajResource.class);

    private static final String ENTITY_NAME = "natjecaj";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatjecajService natjecajService;

    private final NatjecajRepository natjecajRepository;

    public NatjecajResource(NatjecajService natjecajService, NatjecajRepository natjecajRepository) {
        this.natjecajService = natjecajService;
        this.natjecajRepository = natjecajRepository;
    }

    /**
     * {@code POST  /natjecajs} : Create a new natjecaj.
     *
     * @param natjecajDTO the natjecajDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natjecajDTO, or with status {@code 400 (Bad Request)} if the natjecaj has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/natjecajs")
    public ResponseEntity<NatjecajDTO> createNatjecaj(@Valid @RequestBody NatjecajDTO natjecajDTO) throws URISyntaxException {
        log.debug("REST request to save Natjecaj : {}", natjecajDTO);
        if (natjecajDTO.getId() != null) {
            throw new BadRequestAlertException("A new natjecaj cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatjecajDTO result = natjecajService.save(natjecajDTO);
        return ResponseEntity
            .created(new URI("/api/natjecajs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /natjecajs/:id} : Updates an existing natjecaj.
     *
     * @param id the id of the natjecajDTO to save.
     * @param natjecajDTO the natjecajDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natjecajDTO,
     * or with status {@code 400 (Bad Request)} if the natjecajDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natjecajDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/natjecajs/{id}")
    public ResponseEntity<NatjecajDTO> updateNatjecaj(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NatjecajDTO natjecajDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Natjecaj : {}, {}", id, natjecajDTO);
        if (natjecajDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natjecajDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natjecajRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatjecajDTO result = natjecajService.update(natjecajDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natjecajDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /natjecajs/:id} : Partial updates given fields of an existing natjecaj, field will ignore if it is null
     *
     * @param id the id of the natjecajDTO to save.
     * @param natjecajDTO the natjecajDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natjecajDTO,
     * or with status {@code 400 (Bad Request)} if the natjecajDTO is not valid,
     * or with status {@code 404 (Not Found)} if the natjecajDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the natjecajDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/natjecajs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatjecajDTO> partialUpdateNatjecaj(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NatjecajDTO natjecajDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Natjecaj partially : {}, {}", id, natjecajDTO);
        if (natjecajDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natjecajDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natjecajRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatjecajDTO> result = natjecajService.partialUpdate(natjecajDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natjecajDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /natjecajs} : get all the natjecajs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natjecajs in body.
     */
    @GetMapping("/natjecajs")
    public ResponseEntity<List<NatjecajDTO>> getAllNatjecajs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("mobilnost-is-null".equals(filter)) {
            log.debug("REST request to get all Natjecajs where mobilnost is null");
            return new ResponseEntity<>(natjecajService.findAllWhereMobilnostIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Natjecajs");
        return new ResponseEntity<>(natjecajService.findAllFilteredByUserRoleAndEntityType(), HttpStatus.OK);
    }

    /**
     * {@code GET  /natjecajs/:id} : get the "id" natjecaj.
     *
     * @param id the id of the natjecajDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natjecajDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/natjecajs/{id}")
    public ResponseEntity<NatjecajDTO> getNatjecaj(@PathVariable Long id) {
        log.debug("REST request to get Natjecaj : {}", id);
        Optional<NatjecajDTO> natjecajDTO = natjecajService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natjecajDTO);
    }

    /**
     * {@code DELETE  /natjecajs/:id} : delete the "id" natjecaj.
     *
     * @param id the id of the natjecajDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/natjecajs/{id}")
    public ResponseEntity<Void> deleteNatjecaj(@PathVariable Long id) {
        log.debug("REST request to delete Natjecaj : {}", id);
        natjecajService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
