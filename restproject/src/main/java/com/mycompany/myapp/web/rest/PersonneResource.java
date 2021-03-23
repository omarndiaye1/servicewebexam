package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.service.PersonneService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Personne}.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    private static final String ENTITY_NAME = "restprojectPersonne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonneService personneService;

    public PersonneResource(PersonneService personneService) {
        this.personneService = personneService;
    }

    /**
     * {@code POST  /personnes} : Create a new personne.
     *
     * @param personne the personne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personne, or with status {@code 400 (Bad Request)} if the personne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personnes")
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personne);
        if (personne.getId() != null) {
            throw new BadRequestAlertException("A new personne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personne result = personneService.save(personne);
        return ResponseEntity.created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnes} : Updates an existing personne.
     *
     * @param personne the personne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personne,
     * or with status {@code 400 (Bad Request)} if the personne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personnes")
    public ResponseEntity<Personne> updatePersonne(@RequestBody Personne personne) throws URISyntaxException {
        log.debug("REST request to update Personne : {}", personne);
        if (personne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Personne result = personneService.save(personne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personne.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personnes} : get all the personnes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnes in body.
     */
    @GetMapping("/personnes")
    public List<Personne> getAllPersonnes() {
        log.debug("REST request to get all Personnes");
        return personneService.findAll();
    }

    /**
     * {@code GET  /personnes/:id} : get the "id" personne.
     *
     * @param id the id of the personne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personnes/{id}")
    public ResponseEntity<Personne> getPersonne(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        Optional<Personne> personne = personneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personne);
    }

    /**
     * {@code DELETE  /personnes/:id} : delete the "id" personne.
     *
     * @param id the id of the personne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personnes/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
