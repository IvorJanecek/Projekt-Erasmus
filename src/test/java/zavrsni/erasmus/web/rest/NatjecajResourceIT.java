package zavrsni.erasmus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import zavrsni.erasmus.IntegrationTest;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.enumeration.Status;
import zavrsni.erasmus.repository.NatjecajRepository;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.mapper.NatjecajMapper;

/**
 * Integration tests for the {@link NatjecajResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatjecajResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATUM_OD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUM_DO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_DO = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.OTVOREN;
    private static final Status UPDATED_STATUS = Status.ZATVOREN;

    private static final String ENTITY_API_URL = "/api/natjecajs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatjecajRepository natjecajRepository;

    @Autowired
    private NatjecajMapper natjecajMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatjecajMockMvc;

    private Natjecaj natjecaj;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Natjecaj createEntity(EntityManager em) {
        Natjecaj natjecaj = new Natjecaj()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .datumOd(DEFAULT_DATUM_OD)
            .datumDo(DEFAULT_DATUM_DO)
            .status(DEFAULT_STATUS);
        return natjecaj;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Natjecaj createUpdatedEntity(EntityManager em) {
        Natjecaj natjecaj = new Natjecaj()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .datumOd(UPDATED_DATUM_OD)
            .datumDo(UPDATED_DATUM_DO)
            .status(UPDATED_STATUS);
        return natjecaj;
    }

    @BeforeEach
    public void initTest() {
        natjecaj = createEntity(em);
    }

    @Test
    @Transactional
    void createNatjecaj() throws Exception {
        int databaseSizeBeforeCreate = natjecajRepository.findAll().size();
        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);
        restNatjecajMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natjecajDTO)))
            .andExpect(status().isCreated());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeCreate + 1);
        Natjecaj testNatjecaj = natjecajList.get(natjecajList.size() - 1);
        assertThat(testNatjecaj.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNatjecaj.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNatjecaj.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testNatjecaj.getDatumOd()).isEqualTo(DEFAULT_DATUM_OD);
        assertThat(testNatjecaj.getDatumDo()).isEqualTo(DEFAULT_DATUM_DO);
        assertThat(testNatjecaj.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNatjecajWithExistingId() throws Exception {
        // Create the Natjecaj with an existing ID
        natjecaj.setId(1L);
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        int databaseSizeBeforeCreate = natjecajRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatjecajMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natjecajDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = natjecajRepository.findAll().size();
        // set the field null
        natjecaj.setName(null);

        // Create the Natjecaj, which fails.
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        restNatjecajMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natjecajDTO)))
            .andExpect(status().isBadRequest());

        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = natjecajRepository.findAll().size();
        // set the field null
        natjecaj.setDescription(null);

        // Create the Natjecaj, which fails.
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        restNatjecajMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natjecajDTO)))
            .andExpect(status().isBadRequest());

        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNatjecajs() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        // Get all the natjecajList
        restNatjecajMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natjecaj.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].datumOd").value(hasItem(DEFAULT_DATUM_OD.toString())))
            .andExpect(jsonPath("$.[*].datumDo").value(hasItem(DEFAULT_DATUM_DO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getNatjecaj() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        // Get the natjecaj
        restNatjecajMockMvc
            .perform(get(ENTITY_API_URL_ID, natjecaj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natjecaj.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.datumOd").value(DEFAULT_DATUM_OD.toString()))
            .andExpect(jsonPath("$.datumDo").value(DEFAULT_DATUM_DO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNatjecaj() throws Exception {
        // Get the natjecaj
        restNatjecajMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNatjecaj() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();

        // Update the natjecaj
        Natjecaj updatedNatjecaj = natjecajRepository.findById(natjecaj.getId()).get();
        // Disconnect from session so that the updates on updatedNatjecaj are not directly saved in db
        em.detach(updatedNatjecaj);
        updatedNatjecaj
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .datumOd(UPDATED_DATUM_OD)
            .datumDo(UPDATED_DATUM_DO)
            .status(UPDATED_STATUS);
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(updatedNatjecaj);

        restNatjecajMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natjecajDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isOk());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
        Natjecaj testNatjecaj = natjecajList.get(natjecajList.size() - 1);
        assertThat(testNatjecaj.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNatjecaj.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNatjecaj.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testNatjecaj.getDatumOd()).isEqualTo(UPDATED_DATUM_OD);
        assertThat(testNatjecaj.getDatumDo()).isEqualTo(UPDATED_DATUM_DO);
        assertThat(testNatjecaj.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natjecajDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natjecajDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatjecajWithPatch() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();

        // Update the natjecaj using partial update
        Natjecaj partialUpdatedNatjecaj = new Natjecaj();
        partialUpdatedNatjecaj.setId(natjecaj.getId());

        partialUpdatedNatjecaj.name(UPDATED_NAME).datumDo(UPDATED_DATUM_DO);

        restNatjecajMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatjecaj.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatjecaj))
            )
            .andExpect(status().isOk());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
        Natjecaj testNatjecaj = natjecajList.get(natjecajList.size() - 1);
        assertThat(testNatjecaj.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNatjecaj.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNatjecaj.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testNatjecaj.getDatumOd()).isEqualTo(DEFAULT_DATUM_OD);
        assertThat(testNatjecaj.getDatumDo()).isEqualTo(UPDATED_DATUM_DO);
        assertThat(testNatjecaj.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNatjecajWithPatch() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();

        // Update the natjecaj using partial update
        Natjecaj partialUpdatedNatjecaj = new Natjecaj();
        partialUpdatedNatjecaj.setId(natjecaj.getId());

        partialUpdatedNatjecaj
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .datumOd(UPDATED_DATUM_OD)
            .datumDo(UPDATED_DATUM_DO)
            .status(UPDATED_STATUS);

        restNatjecajMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatjecaj.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatjecaj))
            )
            .andExpect(status().isOk());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
        Natjecaj testNatjecaj = natjecajList.get(natjecajList.size() - 1);
        assertThat(testNatjecaj.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNatjecaj.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNatjecaj.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testNatjecaj.getDatumOd()).isEqualTo(UPDATED_DATUM_OD);
        assertThat(testNatjecaj.getDatumDo()).isEqualTo(UPDATED_DATUM_DO);
        assertThat(testNatjecaj.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natjecajDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatjecaj() throws Exception {
        int databaseSizeBeforeUpdate = natjecajRepository.findAll().size();
        natjecaj.setId(count.incrementAndGet());

        // Create the Natjecaj
        NatjecajDTO natjecajDTO = natjecajMapper.toDto(natjecaj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatjecajMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natjecajDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Natjecaj in the database
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatjecaj() throws Exception {
        // Initialize the database
        natjecajRepository.saveAndFlush(natjecaj);

        int databaseSizeBeforeDelete = natjecajRepository.findAll().size();

        // Delete the natjecaj
        restNatjecajMockMvc
            .perform(delete(ENTITY_API_URL_ID, natjecaj.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Natjecaj> natjecajList = natjecajRepository.findAll();
        assertThat(natjecajList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
