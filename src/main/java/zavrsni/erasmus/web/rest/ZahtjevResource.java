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
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.repository.ZahtjevRepository;
import zavrsni.erasmus.service.ZahtjevService;
import zavrsni.erasmus.service.dto.ZahtjevDTO;
import zavrsni.erasmus.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class ZahtjevResource {

    private final Logger log = LoggerFactory.getLogger(ZahtjevResource.class);

    private static final String ENTITY_NAME = "zahtjev";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZahtjevService zahtjevService;

    private final ZahtjevRepository zahtjevRepository;

    public ZahtjevResource(ZahtjevService zahtjevService, ZahtjevRepository zahtjevRepository) {
        this.zahtjevService = zahtjevService;
        this.zahtjevRepository = zahtjevRepository;
    }

    @PostMapping("/zahtjevs")
    public ResponseEntity<ZahtjevDTO> createZahtjev(@Valid @RequestBody ZahtjevDTO zahtjevDTO) throws URISyntaxException {
        log.debug("REST request to save Zahtjev : {}", zahtjevDTO);
        if (zahtjevDTO.getId() != null) {
            throw new BadRequestAlertException("A new zahtjev cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZahtjevDTO result = zahtjevService.save(zahtjevDTO);
        return ResponseEntity
            .created(new URI("/api/zahtjevs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/zahtjevs/{id}")
    public ResponseEntity<ZahtjevDTO> updateZahtjev(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ZahtjevDTO zahtjevDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fakultet : {}, {}", id, zahtjevDTO);
        if (zahtjevDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zahtjevDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zahtjevRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ZahtjevDTO result = zahtjevService.update(zahtjevDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zahtjevDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/zahtjevs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ZahtjevDTO> partialUpdateZahtjev(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ZahtjevDTO zahtjevDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fakultet partially : {}, {}", id, zahtjevDTO);
        if (zahtjevDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zahtjevDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zahtjevRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ZahtjevDTO> result = zahtjevService.partialUpdate(zahtjevDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zahtjevDTO.getId().toString())
        );
    }

    @GetMapping("/zahtjevs")
    public List<ZahtjevDTO> getAllZahtjevs() {
        log.debug("REST request to get all Zahtjevs");
        return zahtjevService.findAll();
    }

    @GetMapping("/zahtjevs/{id}")
    public ResponseEntity<ZahtjevDTO> getZahtjev(@PathVariable Long id) {
        log.debug("REST request to get Zahtjev : {}", id);
        Optional<ZahtjevDTO> zahtjevDTO = zahtjevService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zahtjevDTO);
    }

    @DeleteMapping("/zahtjevs/{id}")
    public ResponseEntity<Void> deleteZahtjev(@PathVariable Long id) {
        log.debug("REST request to delete Zahtjev : {}", id);
        zahtjevService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
