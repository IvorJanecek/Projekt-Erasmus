package zavrsni.erasmus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import zavrsni.erasmus.IntegrationTest;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.enumeration.Kategorija;
import zavrsni.erasmus.repository.PrijavaRepository;
import zavrsni.erasmus.service.PrijavaService;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.mapper.PrijavaMapper;

/**
 * Integration tests for the {@link PrijavaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PrijavaResourceIT {

    private static final String DEFAULT_PRIJAVA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIJAVA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PRIHVACEN = false;
    private static final Boolean UPDATED_PRIHVACEN = true;

    private static final LocalDate DEFAULT_TRAJANJE_OD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRAJANJE_OD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TRAJANJE_DO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRAJANJE_DO = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final Kategorija DEFAULT_KATEGORIJA = Kategorija.NASTAVA;
    private static final Kategorija UPDATED_KATEGORIJA = Kategorija.STRUCNA_PRAKSA;

    private static final String ENTITY_API_URL = "/api/prijavas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Mock
    private PrijavaRepository prijavaRepositoryMock;

    @Autowired
    private PrijavaMapper prijavaMapper;

    @Mock
    private PrijavaService prijavaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrijavaMockMvc;

    private Prijava prijava;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijava createEntity(EntityManager em) {
        Prijava prijava = new Prijava()
            .prijavaName(DEFAULT_PRIJAVA_NAME)
            .opis(DEFAULT_OPIS)
            .createdDate(DEFAULT_CREATED_DATE)
            .prihvacen(DEFAULT_PRIHVACEN)
            .trajanjeOd(DEFAULT_TRAJANJE_OD)
            .trajanjeDo(DEFAULT_TRAJANJE_DO)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .kategorija(DEFAULT_KATEGORIJA);
        return prijava;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijava createUpdatedEntity(EntityManager em) {
        Prijava prijava = new Prijava()
            .prijavaName(UPDATED_PRIJAVA_NAME)
            .opis(UPDATED_OPIS)
            .createdDate(UPDATED_CREATED_DATE)
            .prihvacen(UPDATED_PRIHVACEN)
            .trajanjeOd(UPDATED_TRAJANJE_OD)
            .trajanjeDo(UPDATED_TRAJANJE_DO)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .kategorija(UPDATED_KATEGORIJA);
        return prijava;
    }

    @BeforeEach
    public void initTest() {
        prijava = createEntity(em);
    }

    @Test
    @Transactional
    void createPrijava() throws Exception {
        int databaseSizeBeforeCreate = prijavaRepository.findAll().size();
        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);
        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isCreated());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeCreate + 1);
        Prijava testPrijava = prijavaList.get(prijavaList.size() - 1);
        assertThat(testPrijava.getPrijavaName()).isEqualTo(DEFAULT_PRIJAVA_NAME);
        assertThat(testPrijava.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testPrijava.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPrijava.getPrihvacen()).isEqualTo(DEFAULT_PRIHVACEN);
        assertThat(testPrijava.getTrajanjeOd()).isEqualTo(DEFAULT_TRAJANJE_OD);
        assertThat(testPrijava.getTrajanjeDo()).isEqualTo(DEFAULT_TRAJANJE_DO);
        assertThat(testPrijava.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPrijava.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testPrijava.getKategorija()).isEqualTo(DEFAULT_KATEGORIJA);
    }

    @Test
    @Transactional
    void createPrijavaWithExistingId() throws Exception {
        // Create the Prijava with an existing ID
        prijava.setId(1L);
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        int databaseSizeBeforeCreate = prijavaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrijavaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prijavaRepository.findAll().size();
        // set the field null
        prijava.setPrijavaName(null);

        // Create the Prijava, which fails.
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isBadRequest());

        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpisIsRequired() throws Exception {
        int databaseSizeBeforeTest = prijavaRepository.findAll().size();
        // set the field null
        prijava.setOpis(null);

        // Create the Prijava, which fails.
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isBadRequest());

        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrajanjeOdIsRequired() throws Exception {
        int databaseSizeBeforeTest = prijavaRepository.findAll().size();
        // set the field null
        prijava.setTrajanjeOd(null);

        // Create the Prijava, which fails.
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isBadRequest());

        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrajanjeDoIsRequired() throws Exception {
        int databaseSizeBeforeTest = prijavaRepository.findAll().size();
        // set the field null
        prijava.setTrajanjeDo(null);

        // Create the Prijava, which fails.
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        restPrijavaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isBadRequest());

        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrijavas() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        // Get all the prijavaList
        restPrijavaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijava.getId().intValue())))
            .andExpect(jsonPath("$.[*].prijavaName").value(hasItem(DEFAULT_PRIJAVA_NAME)))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].prihvacen").value(hasItem(DEFAULT_PRIHVACEN.booleanValue())))
            .andExpect(jsonPath("$.[*].trajanjeOd").value(hasItem(DEFAULT_TRAJANJE_OD.toString())))
            .andExpect(jsonPath("$.[*].trajanjeDo").value(hasItem(DEFAULT_TRAJANJE_DO.toString())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].kategorija").value(hasItem(DEFAULT_KATEGORIJA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrijavasWithEagerRelationshipsIsEnabled() throws Exception {
        when(prijavaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrijavaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(prijavaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrijavasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(prijavaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrijavaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(prijavaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPrijava() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        // Get the prijava
        restPrijavaMockMvc
            .perform(get(ENTITY_API_URL_ID, prijava.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prijava.getId().intValue()))
            .andExpect(jsonPath("$.prijavaName").value(DEFAULT_PRIJAVA_NAME))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.prihvacen").value(DEFAULT_PRIHVACEN.booleanValue()))
            .andExpect(jsonPath("$.trajanjeOd").value(DEFAULT_TRAJANJE_OD.toString()))
            .andExpect(jsonPath("$.trajanjeDo").value(DEFAULT_TRAJANJE_DO.toString()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.kategorija").value(DEFAULT_KATEGORIJA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrijava() throws Exception {
        // Get the prijava
        restPrijavaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrijava() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();

        // Update the prijava
        Prijava updatedPrijava = prijavaRepository.findById(prijava.getId()).get();
        // Disconnect from session so that the updates on updatedPrijava are not directly saved in db
        em.detach(updatedPrijava);
        updatedPrijava
            .prijavaName(UPDATED_PRIJAVA_NAME)
            .opis(UPDATED_OPIS)
            .createdDate(UPDATED_CREATED_DATE)
            .prihvacen(UPDATED_PRIHVACEN)
            .trajanjeOd(UPDATED_TRAJANJE_OD)
            .trajanjeDo(UPDATED_TRAJANJE_DO)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .kategorija(UPDATED_KATEGORIJA);
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(updatedPrijava);

        restPrijavaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prijavaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
        Prijava testPrijava = prijavaList.get(prijavaList.size() - 1);
        assertThat(testPrijava.getPrijavaName()).isEqualTo(UPDATED_PRIJAVA_NAME);
        assertThat(testPrijava.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testPrijava.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPrijava.getPrihvacen()).isEqualTo(UPDATED_PRIHVACEN);
        assertThat(testPrijava.getTrajanjeOd()).isEqualTo(UPDATED_TRAJANJE_OD);
        assertThat(testPrijava.getTrajanjeDo()).isEqualTo(UPDATED_TRAJANJE_DO);
        assertThat(testPrijava.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPrijava.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testPrijava.getKategorija()).isEqualTo(UPDATED_KATEGORIJA);
    }

    @Test
    @Transactional
    void putNonExistingPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prijavaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prijavaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrijavaWithPatch() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();

        // Update the prijava using partial update
        Prijava partialUpdatedPrijava = new Prijava();
        partialUpdatedPrijava.setId(prijava.getId());

        partialUpdatedPrijava.prihvacen(UPDATED_PRIHVACEN).trajanjeOd(UPDATED_TRAJANJE_OD).kategorija(UPDATED_KATEGORIJA);

        restPrijavaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijava.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrijava))
            )
            .andExpect(status().isOk());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
        Prijava testPrijava = prijavaList.get(prijavaList.size() - 1);
        assertThat(testPrijava.getPrijavaName()).isEqualTo(DEFAULT_PRIJAVA_NAME);
        assertThat(testPrijava.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testPrijava.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPrijava.getPrihvacen()).isEqualTo(UPDATED_PRIHVACEN);
        assertThat(testPrijava.getTrajanjeOd()).isEqualTo(UPDATED_TRAJANJE_OD);
        assertThat(testPrijava.getTrajanjeDo()).isEqualTo(DEFAULT_TRAJANJE_DO);
        assertThat(testPrijava.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPrijava.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testPrijava.getKategorija()).isEqualTo(UPDATED_KATEGORIJA);
    }

    @Test
    @Transactional
    void fullUpdatePrijavaWithPatch() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();

        // Update the prijava using partial update
        Prijava partialUpdatedPrijava = new Prijava();
        partialUpdatedPrijava.setId(prijava.getId());

        partialUpdatedPrijava
            .prijavaName(UPDATED_PRIJAVA_NAME)
            .opis(UPDATED_OPIS)
            .createdDate(UPDATED_CREATED_DATE)
            .prihvacen(UPDATED_PRIHVACEN)
            .trajanjeOd(UPDATED_TRAJANJE_OD)
            .trajanjeDo(UPDATED_TRAJANJE_DO)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .kategorija(UPDATED_KATEGORIJA);

        restPrijavaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijava.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrijava))
            )
            .andExpect(status().isOk());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
        Prijava testPrijava = prijavaList.get(prijavaList.size() - 1);
        assertThat(testPrijava.getPrijavaName()).isEqualTo(UPDATED_PRIJAVA_NAME);
        assertThat(testPrijava.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testPrijava.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPrijava.getPrihvacen()).isEqualTo(UPDATED_PRIHVACEN);
        assertThat(testPrijava.getTrajanjeOd()).isEqualTo(UPDATED_TRAJANJE_OD);
        assertThat(testPrijava.getTrajanjeDo()).isEqualTo(UPDATED_TRAJANJE_DO);
        assertThat(testPrijava.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPrijava.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testPrijava.getKategorija()).isEqualTo(UPDATED_KATEGORIJA);
    }

    @Test
    @Transactional
    void patchNonExistingPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prijavaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrijava() throws Exception {
        int databaseSizeBeforeUpdate = prijavaRepository.findAll().size();
        prijava.setId(count.incrementAndGet());

        // Create the Prijava
        PrijavaDTO prijavaDTO = prijavaMapper.toDto(prijava);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijavaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prijavaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijava in the database
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrijava() throws Exception {
        // Initialize the database
        prijavaRepository.saveAndFlush(prijava);

        int databaseSizeBeforeDelete = prijavaRepository.findAll().size();

        // Delete the prijava
        restPrijavaMockMvc
            .perform(delete(ENTITY_API_URL_ID, prijava.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prijava> prijavaList = prijavaRepository.findAll();
        assertThat(prijavaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
