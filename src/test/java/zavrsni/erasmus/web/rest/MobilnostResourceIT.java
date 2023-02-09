package zavrsni.erasmus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
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
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.repository.MobilnostRepository;
import zavrsni.erasmus.service.MobilnostService;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.service.mapper.MobilnostMapper;

/**
 * Integration tests for the {@link MobilnostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MobilnostResourceIT {

    private static final String DEFAULT_MOBILNOST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOBILNOST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/mobilnosts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MobilnostRepository mobilnostRepository;

    @Mock
    private MobilnostRepository mobilnostRepositoryMock;

    @Autowired
    private MobilnostMapper mobilnostMapper;

    @Mock
    private MobilnostService mobilnostServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobilnostMockMvc;

    private Mobilnost mobilnost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mobilnost createEntity(EntityManager em) {
        Mobilnost mobilnost = new Mobilnost()
            .mobilnostName(DEFAULT_MOBILNOST_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdDate(DEFAULT_CREATED_DATE)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return mobilnost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mobilnost createUpdatedEntity(EntityManager em) {
        Mobilnost mobilnost = new Mobilnost()
            .mobilnostName(UPDATED_MOBILNOST_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return mobilnost;
    }

    @BeforeEach
    public void initTest() {
        mobilnost = createEntity(em);
    }

    @Test
    @Transactional
    void createMobilnost() throws Exception {
        int databaseSizeBeforeCreate = mobilnostRepository.findAll().size();
        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);
        restMobilnostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mobilnostDTO)))
            .andExpect(status().isCreated());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeCreate + 1);
        Mobilnost testMobilnost = mobilnostList.get(mobilnostList.size() - 1);
        assertThat(testMobilnost.getMobilnostName()).isEqualTo(DEFAULT_MOBILNOST_NAME);
        assertThat(testMobilnost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMobilnost.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMobilnost.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMobilnost.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createMobilnostWithExistingId() throws Exception {
        // Create the Mobilnost with an existing ID
        mobilnost.setId(1L);
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        int databaseSizeBeforeCreate = mobilnostRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobilnostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mobilnostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMobilnostNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobilnostRepository.findAll().size();
        // set the field null
        mobilnost.setMobilnostName(null);

        // Create the Mobilnost, which fails.
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        restMobilnostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mobilnostDTO)))
            .andExpect(status().isBadRequest());

        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobilnostRepository.findAll().size();
        // set the field null
        mobilnost.setDescription(null);

        // Create the Mobilnost, which fails.
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        restMobilnostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mobilnostDTO)))
            .andExpect(status().isBadRequest());

        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMobilnosts() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        // Get all the mobilnostList
        restMobilnostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobilnost.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilnostName").value(hasItem(DEFAULT_MOBILNOST_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMobilnostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(mobilnostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMobilnostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mobilnostServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMobilnostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mobilnostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMobilnostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mobilnostRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMobilnost() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        // Get the mobilnost
        restMobilnostMockMvc
            .perform(get(ENTITY_API_URL_ID, mobilnost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobilnost.getId().intValue()))
            .andExpect(jsonPath("$.mobilnostName").value(DEFAULT_MOBILNOST_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingMobilnost() throws Exception {
        // Get the mobilnost
        restMobilnostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMobilnost() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();

        // Update the mobilnost
        Mobilnost updatedMobilnost = mobilnostRepository.findById(mobilnost.getId()).get();
        // Disconnect from session so that the updates on updatedMobilnost are not directly saved in db
        em.detach(updatedMobilnost);
        updatedMobilnost
            .mobilnostName(UPDATED_MOBILNOST_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(updatedMobilnost);

        restMobilnostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mobilnostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isOk());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
        Mobilnost testMobilnost = mobilnostList.get(mobilnostList.size() - 1);
        assertThat(testMobilnost.getMobilnostName()).isEqualTo(UPDATED_MOBILNOST_NAME);
        assertThat(testMobilnost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMobilnost.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMobilnost.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMobilnost.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mobilnostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mobilnostDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMobilnostWithPatch() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();

        // Update the mobilnost using partial update
        Mobilnost partialUpdatedMobilnost = new Mobilnost();
        partialUpdatedMobilnost.setId(mobilnost.getId());

        partialUpdatedMobilnost
            .mobilnostName(UPDATED_MOBILNOST_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);

        restMobilnostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMobilnost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMobilnost))
            )
            .andExpect(status().isOk());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
        Mobilnost testMobilnost = mobilnostList.get(mobilnostList.size() - 1);
        assertThat(testMobilnost.getMobilnostName()).isEqualTo(UPDATED_MOBILNOST_NAME);
        assertThat(testMobilnost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMobilnost.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMobilnost.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMobilnost.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMobilnostWithPatch() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();

        // Update the mobilnost using partial update
        Mobilnost partialUpdatedMobilnost = new Mobilnost();
        partialUpdatedMobilnost.setId(mobilnost.getId());

        partialUpdatedMobilnost
            .mobilnostName(UPDATED_MOBILNOST_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);

        restMobilnostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMobilnost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMobilnost))
            )
            .andExpect(status().isOk());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
        Mobilnost testMobilnost = mobilnostList.get(mobilnostList.size() - 1);
        assertThat(testMobilnost.getMobilnostName()).isEqualTo(UPDATED_MOBILNOST_NAME);
        assertThat(testMobilnost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMobilnost.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMobilnost.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMobilnost.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mobilnostDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMobilnost() throws Exception {
        int databaseSizeBeforeUpdate = mobilnostRepository.findAll().size();
        mobilnost.setId(count.incrementAndGet());

        // Create the Mobilnost
        MobilnostDTO mobilnostDTO = mobilnostMapper.toDto(mobilnost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilnostMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mobilnostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mobilnost in the database
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMobilnost() throws Exception {
        // Initialize the database
        mobilnostRepository.saveAndFlush(mobilnost);

        int databaseSizeBeforeDelete = mobilnostRepository.findAll().size();

        // Delete the mobilnost
        restMobilnostMockMvc
            .perform(delete(ENTITY_API_URL_ID, mobilnost.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mobilnost> mobilnostList = mobilnostRepository.findAll();
        assertThat(mobilnostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
