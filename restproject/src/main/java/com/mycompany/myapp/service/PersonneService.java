package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.repository.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Personne}.
 */
@Service
@Transactional
public class PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneService.class);

    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    /**
     * Save a personne.
     *
     * @param personne the entity to save.
     * @return the persisted entity.
     */
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        return personneRepository.save(personne);
    }

    /**
     * Get all the personnes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Personne> findAll() {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll();
    }


    /**
     * Get one personne by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Personne> findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        return personneRepository.findById(id);
    }

    /**
     * Delete the personne by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.deleteById(id);
    }
}
