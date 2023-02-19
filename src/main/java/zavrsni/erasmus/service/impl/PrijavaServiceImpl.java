package zavrsni.erasmus.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.User;
import zavrsni.erasmus.repository.PrijavaRepository;
import zavrsni.erasmus.service.PrijavaService;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.mapper.PrijavaMapper;

/**
 * Service Implementation for managing {@link Prijava}.
 */
@Service
@Transactional
public class PrijavaServiceImpl implements PrijavaService {

    private final Logger log = LoggerFactory.getLogger(PrijavaServiceImpl.class);

    private final PrijavaRepository prijavaRepository;

    private final PrijavaMapper prijavaMapper;

    public PrijavaServiceImpl(PrijavaRepository prijavaRepository, PrijavaMapper prijavaMapper) {
        this.prijavaRepository = prijavaRepository;
        this.prijavaMapper = prijavaMapper;
    }

    @Override
    public PrijavaDTO save(PrijavaDTO prijavaDTO) {
        log.debug("Request to save Prijava : {}", prijavaDTO);
        Prijava prijava = prijavaMapper.toEntity(prijavaDTO);
        prijava = prijavaRepository.save(prijava);
        return prijavaMapper.toDto(prijava);
    }

    @Override
    public PrijavaDTO update(PrijavaDTO prijavaDTO) {
        log.debug("Request to update Prijava : {}", prijavaDTO);
        Prijava prijava = prijavaMapper.toEntity(prijavaDTO);
        prijava = prijavaRepository.save(prijava);
        return prijavaMapper.toDto(prijava);
    }

    @Override
    public Optional<PrijavaDTO> partialUpdate(PrijavaDTO prijavaDTO) {
        log.debug("Request to partially update Prijava : {}", prijavaDTO);

        return prijavaRepository
            .findById(prijavaDTO.getId())
            .map(existingPrijava -> {
                prijavaMapper.partialUpdate(existingPrijava, prijavaDTO);

                return existingPrijava;
            })
            .map(prijavaRepository::save)
            .map(prijavaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrijavaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prijavas");
        return prijavaRepository.findAll(pageable).map(prijavaMapper::toDto);
    }

    public Page<PrijavaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return prijavaRepository.findAllWithEagerRelationships(pageable).map(prijavaMapper::toDto);
    }

    /**
     *  Get all the prijavas where Mobilnost is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrijavaDTO> findAllWhereMobilnostIsNull() {
        log.debug("Request to get all prijavas where Mobilnost is null");
        return StreamSupport
            .stream(prijavaRepository.findAll().spliterator(), false)
            .filter(prijava -> prijava.getMobilnost() == null)
            .map(prijavaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrijavaDTO> findOne(Long id) {
        log.debug("Request to get Prijava : {}", id);
        return prijavaRepository.findOneWithEagerRelationships(id).map(prijavaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prijava : {}", id);
        prijavaRepository.deleteById(id);
    }

    @Override
    public boolean hasUserApplied(Natjecaj natjecaj, User user) {
        Optional<Prijava> prijavaOptional = prijavaRepository.findByNatjecajAndUser(natjecaj, user);
        return prijavaOptional.isPresent();
    }
}
