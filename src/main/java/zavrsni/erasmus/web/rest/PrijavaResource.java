package zavrsni.erasmus.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.User;
import zavrsni.erasmus.repository.NatjecajRepository;
import zavrsni.erasmus.repository.PrijavaRepository;
import zavrsni.erasmus.repository.UserRepository;
import zavrsni.erasmus.security.SecurityUtils;
import zavrsni.erasmus.service.PrijavaService;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zavrsni.erasmus.domain.Prijava}.
 */
@RestController
@RequestMapping("/api")
public class PrijavaResource {

    private final Logger log = LoggerFactory.getLogger(PrijavaResource.class);

    private static final String ENTITY_NAME = "prijava";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijavaService prijavaService;

    private final PrijavaRepository prijavaRepository;
    private final NatjecajRepository natjecajRepository;
    private final UserRepository userRepository;

    public PrijavaResource(
        NatjecajRepository natjecajRepository,
        PrijavaService prijavaService,
        PrijavaRepository prijavaRepository,
        UserRepository userRepository
    ) {
        this.prijavaService = prijavaService;
        this.prijavaRepository = prijavaRepository;
        this.userRepository = userRepository;
        this.natjecajRepository = natjecajRepository;
    }

    /**
     * {@code POST  /prijavas} : Create a new prijava.
     *
     * @param prijavaDTO the prijavaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijavaDTO, or with status {@code 400 (Bad Request)} if the prijava has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prijavas")
    public ResponseEntity<PrijavaDTO> createPrijava(@Valid @RequestBody PrijavaDTO prijavaDTO) throws URISyntaxException {
        log.debug("REST request to save Prijava : {}", prijavaDTO);
        if (prijavaDTO.getId() != null) {
            throw new BadRequestAlertException("A new prijava cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // Get the Natjecaj entity associated with the Prijava entity
        Natjecaj natjecaj = natjecajRepository
            .findById(prijavaDTO.getNatjecaj().getId())
            .orElseThrow(() -> new EntityNotFoundException("Natjecaj not found"));

        // Get the user creating the Prijava entity
        User user = userRepository
            .findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null))
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check if user has already created a Prijava entity for this Natjecaj
        if (prijavaService.hasUserApplied(natjecaj, user)) {
            return ResponseEntity.badRequest().build();
        }

        // Create and save new Prijava entity
        PrijavaDTO result = prijavaService.save(prijavaDTO);
        return ResponseEntity
            .created(new URI("/api/prijavas/" + result.getId()))
            .headers(HHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prijavas/:id} : Updates an existing prijava.
     *
     * @param id the id of the prijavaDTO to save.
     * @param prijavaDTO the prijavaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijavaDTO,
     * or with status {@code 400 (Bad Request)} if the prijavaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prijavaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prijavas/{id}")
    public ResponseEntity<PrijavaDTO> updatePrijava(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PrijavaDTO prijavaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Prijava : {}, {}", id, prijavaDTO);
        if (prijavaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijavaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijavaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrijavaDTO result = prijavaService.update(prijavaDTO);
        return ResponseEntity
            .ok()
            .headers(HHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijavaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/prijavas/list")
    public ResponseEntity<List<PrijavaDTO>> getAllPrijavasForAdmin(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("mobilnost-is-null".equals(filter)) {
            log.debug("REST request to get all Prijavas where mobilnost is null");
            return new ResponseEntity<>(prijavaService.findAllWhereMobilnostIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Prijavas");
        Page<PrijavaDTO> page;
        if (eagerload) {
            page = prijavaService.findAllWithEagerRelationships(pageable);
        } else {
            page = prijavaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code PATCH  /prijavas/:id} : Partial updates given fields of an existing prijava, field will ignore if it is null
     *
     * @param id the id of the prijavaDTO to save.
     * @param prijavaDTO the prijavaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijavaDTO,
     * or with status {@code 400 (Bad Request)} if the prijavaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prijavaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prijavaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prijavas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrijavaDTO> partialUpdatePrijava(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PrijavaDTO prijavaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prijava partially : {}, {}", id, prijavaDTO);
        if (prijavaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijavaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijavaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrijavaDTO> result = prijavaService.partialUpdate(prijavaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijavaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prijavas} : get all the prijavas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijavas in body.
     */
    @GetMapping("/prijavas")
    public ResponseEntity<List<PrijavaDTO>> getAllPrijavas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("mobilnost-is-null".equals(filter)) {
            log.debug("REST request to get all Prijavas where mobilnost is null");
            return new ResponseEntity<>(prijavaService.findAllWhereMobilnostIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Prijavas");
        Page<PrijavaDTO> page;
        if (eagerload) {
            page = prijavaService.findByUserIsCurrentUserWithEagerRelationships(pageable);
        } else {
            page = prijavaService.findByUserIsCurrentUser(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prijavas/:id} : get the "id" prijava.
     *
     * @param id the id of the prijavaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijavaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prijavas/{id}")
    public ResponseEntity<PrijavaDTO> getPrijava(@PathVariable Long id) {
        log.debug("REST request to get Prijava : {}", id);

        // Get the login of the currently logged-in user
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
        // Retrieve the Prijava entity by ID
        Optional<PrijavaDTO> prijavaDTO = prijavaService.findOne(id);
        // Check if the current user has admin role
        boolean isAdmin = SecurityUtils.hasCurrentUserAdminRole();

        // Check if the Prijava entity exists and was created by the current user
        if (prijavaDTO.isPresent() && (prijavaDTO.get().getUser().getLogin().equals(currentUserLogin) || isAdmin)) {
            return ResponseUtil.wrapOrNotFound(prijavaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * {@code DELETE  /prijavas/:id} : delete the "id" prijava.
     *
     * @param id the id of the prijavaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prijavas/{id}")
    public ResponseEntity<Void> deletePrijava(@PathVariable Long id) {
        log.debug("REST request to delete Prijava : {}", id);
        prijavaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
