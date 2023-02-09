package zavrsni.erasmus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import zavrsni.erasmus.domain.Fakultet;
import zavrsni.erasmus.repository.FakultetRepository;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.mapper.FakultetMapper;

/**
 * Integration tests for the {@link FakultetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FakultetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fakultets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FakultetRepository fakultetRepository;

    @Autowired
    private FakultetMapper fakultetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFakultetMockMvc;

    private Fakultet fakultet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fakultet createEntity(EntityManager em) {
        Fakultet fakultet = new Fakultet().name(DEFAULT_NAME);
        return fakultet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fakultet createUpdatedEntity(EntityManager em) {
        Fakultet fakultet = new Fakultet().name(UPDATED_NAME);
        return fakultet;
    }

    @BeforeEach
    public void initTest() {
        fakultet = createEntity(em);
    }

    @Test
    @Transactional
    void createFakultet() throws Exception {
        int databaseSizeBeforeCreate = fakultetRepository.findAll().size();
        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);
        restFakultetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fakultetDTO)))
            .andExpect(status().isCreated());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeCreate + 1);
        Fakultet testFakultet = fakultetList.get(fakultetList.size() - 1);
        assertThat(testFakultet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createFakultetWithExistingId() throws Exception {
        // Create the Fakultet with an existing ID
        fakultet.setId(1L);
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        int databaseSizeBeforeCreate = fakultetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFakultetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fakultetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fakultetRepository.findAll().size();
        // set the field null
        fakultet.setName(null);

        // Create the Fakultet, which fails.
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        restFakultetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fakultetDTO)))
            .andExpect(status().isBadRequest());

        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFakultets() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        // Get all the fakultetList
        restFakultetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fakultet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getFakultet() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        // Get the fakultet
        restFakultetMockMvc
            .perform(get(ENTITY_API_URL_ID, fakultet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fakultet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingFakultet() throws Exception {
        // Get the fakultet
        restFakultetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFakultet() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();

        // Update the fakultet
        Fakultet updatedFakultet = fakultetRepository.findById(fakultet.getId()).get();
        // Disconnect from session so that the updates on updatedFakultet are not directly saved in db
        em.detach(updatedFakultet);
        updatedFakultet.name(UPDATED_NAME);
        FakultetDTO fakultetDTO = fakultetMapper.toDto(updatedFakultet);

        restFakultetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fakultetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
        Fakultet testFakultet = fakultetList.get(fakultetList.size() - 1);
        assertThat(testFakultet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fakultetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fakultetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFakultetWithPatch() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();

        // Update the fakultet using partial update
        Fakultet partialUpdatedFakultet = new Fakultet();
        partialUpdatedFakultet.setId(fakultet.getId());

        restFakultetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFakultet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFakultet))
            )
            .andExpect(status().isOk());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
        Fakultet testFakultet = fakultetList.get(fakultetList.size() - 1);
        assertThat(testFakultet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateFakultetWithPatch() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();

        // Update the fakultet using partial update
        Fakultet partialUpdatedFakultet = new Fakultet();
        partialUpdatedFakultet.setId(fakultet.getId());

        partialUpdatedFakultet.name(UPDATED_NAME);

        restFakultetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFakultet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFakultet))
            )
            .andExpect(status().isOk());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
        Fakultet testFakultet = fakultetList.get(fakultetList.size() - 1);
        assertThat(testFakultet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fakultetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFakultet() throws Exception {
        int databaseSizeBeforeUpdate = fakultetRepository.findAll().size();
        fakultet.setId(count.incrementAndGet());

        // Create the Fakultet
        FakultetDTO fakultetDTO = fakultetMapper.toDto(fakultet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFakultetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fakultetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fakultet in the database
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFakultet() throws Exception {
        // Initialize the database
        fakultetRepository.saveAndFlush(fakultet);

        int databaseSizeBeforeDelete = fakultetRepository.findAll().size();

        // Delete the fakultet
        restFakultetMockMvc
            .perform(delete(ENTITY_API_URL_ID, fakultet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        assertThat(fakultetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
