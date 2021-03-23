package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RestprojectApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.repository.PersonneRepository;
import com.mycompany.myapp.service.PersonneService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@SpringBootTest(classes = { RestprojectApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PersonneResourceIT {

    private static final String DEFAULT_NBRTEST = "AAAAAAAAAA";
    private static final String UPDATED_NBRTEST = "BBBBBBBBBB";

    private static final String DEFAULT_POSITIF = "AAAAAAAAAA";
    private static final String UPDATED_POSITIF = "BBBBBBBBBB";

    private static final String DEFAULT_CASCOMMUNAUTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_CASCOMMUNAUTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECES = "AAAAAAAAAA";
    private static final String UPDATED_DECES = "BBBBBBBBBB";

    private static final String DEFAULT_SUIVIS = "AAAAAAAAAA";
    private static final String UPDATED_SUIVIS = "BBBBBBBBBB";

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonneMockMvc;

    private Personne personne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .nbrtest(DEFAULT_NBRTEST)
            .positif(DEFAULT_POSITIF)
            .cascommunautaire(DEFAULT_CASCOMMUNAUTAIRE)
            .deces(DEFAULT_DECES)
            .suivis(DEFAULT_SUIVIS);
        return personne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity(EntityManager em) {
        Personne personne = new Personne()
            .nbrtest(UPDATED_NBRTEST)
            .positif(UPDATED_POSITIF)
            .cascommunautaire(UPDATED_CASCOMMUNAUTAIRE)
            .deces(UPDATED_DECES)
            .suivis(UPDATED_SUIVIS);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();
        // Create the Personne
        restPersonneMockMvc.perform(post("/api/personnes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNbrtest()).isEqualTo(DEFAULT_NBRTEST);
        assertThat(testPersonne.getPositif()).isEqualTo(DEFAULT_POSITIF);
        assertThat(testPersonne.getCascommunautaire()).isEqualTo(DEFAULT_CASCOMMUNAUTAIRE);
        assertThat(testPersonne.getDeces()).isEqualTo(DEFAULT_DECES);
        assertThat(testPersonne.getSuivis()).isEqualTo(DEFAULT_SUIVIS);
    }

    @Test
    @Transactional
    public void createPersonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne with an existing ID
        personne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc.perform(post("/api/personnes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc.perform(get("/api/personnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbrtest").value(hasItem(DEFAULT_NBRTEST)))
            .andExpect(jsonPath("$.[*].positif").value(hasItem(DEFAULT_POSITIF)))
            .andExpect(jsonPath("$.[*].cascommunautaire").value(hasItem(DEFAULT_CASCOMMUNAUTAIRE)))
            .andExpect(jsonPath("$.[*].deces").value(hasItem(DEFAULT_DECES)))
            .andExpect(jsonPath("$.[*].suivis").value(hasItem(DEFAULT_SUIVIS)));
    }
    
    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nbrtest").value(DEFAULT_NBRTEST))
            .andExpect(jsonPath("$.positif").value(DEFAULT_POSITIF))
            .andExpect(jsonPath("$.cascommunautaire").value(DEFAULT_CASCOMMUNAUTAIRE))
            .andExpect(jsonPath("$.deces").value(DEFAULT_DECES))
            .andExpect(jsonPath("$.suivis").value(DEFAULT_SUIVIS));
    }
    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneService.save(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .nbrtest(UPDATED_NBRTEST)
            .positif(UPDATED_POSITIF)
            .cascommunautaire(UPDATED_CASCOMMUNAUTAIRE)
            .deces(UPDATED_DECES)
            .suivis(UPDATED_SUIVIS);

        restPersonneMockMvc.perform(put("/api/personnes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonne)))
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNbrtest()).isEqualTo(UPDATED_NBRTEST);
        assertThat(testPersonne.getPositif()).isEqualTo(UPDATED_POSITIF);
        assertThat(testPersonne.getCascommunautaire()).isEqualTo(UPDATED_CASCOMMUNAUTAIRE);
        assertThat(testPersonne.getDeces()).isEqualTo(UPDATED_DECES);
        assertThat(testPersonne.getSuivis()).isEqualTo(UPDATED_SUIVIS);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc.perform(put("/api/personnes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneService.save(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
