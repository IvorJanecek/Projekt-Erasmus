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
import zavrsni.erasmus.repository.NatjecajRepository;
import zavrsni.erasmus.service.NatjecajService;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.mapper.NatjecajMapper;

/**
 * Service Implementation for managing {@link Natjecaj}.
 */
@Service
@Transactional
public class NatjecajServiceImpl implements NatjecajService {

    private final Logger log = LoggerFactory.getLogger(NatjecajServiceImpl.class);

    private final NatjecajRepository natjecajRepository;

    private final NatjecajMapper natjecajMapper;

    public NatjecajServiceImpl(NatjecajRepository natjecajRepository, NatjecajMapper natjecajMapper) {
        this.natjecajRepository = natjecajRepository;
        this.natjecajMapper = natjecajMapper;
    }

    @Override
    public NatjecajDTO save(NatjecajDTO natjecajDTO) {
        log.debug("Request to save Natjecaj : {}", natjecajDTO);
        Natjecaj natjecaj = natjecajMapper.toEntity(natjecajDTO);
        natjecaj = natjecajRepository.save(natjecaj);
        return natjecajMapper.toDto(natjecaj);
    }

    @Override
    public NatjecajDTO update(NatjecajDTO natjecajDTO) {
        log.debug("Request to update Natjecaj : {}", natjecajDTO);
        Natjecaj natjecaj = natjecajMapper.toEntity(natjecajDTO);
        natjecaj = natjecajRepository.save(natjecaj);
        return natjecajMapper.toDto(natjecaj);
    }

    @Override
    public Optional<NatjecajDTO> partialUpdate(NatjecajDTO natjecajDTO) {
        log.debug("Request to partially update Natjecaj : {}", natjecajDTO);

        return natjecajRepository
            .findById(natjecajDTO.getId())
            .map(existingNatjecaj -> {
                natjecajMapper.partialUpdate(existingNatjecaj, natjecajDTO);

                return existingNatjecaj;
            })
            .map(natjecajRepository::save)
            .map(natjecajMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatjecajDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Natjecajs");
        return natjecajRepository.findAll(pageable).map(natjecajMapper::toDto);
    }

    /**
     *  Get all the natjecajs where Mobilnost is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NatjecajDTO> findAllWhereMobilnostIsNull() {
        log.debug("Request to get all natjecajs where Mobilnost is null");
        return StreamSupport
            .stream(natjecajRepository.findAll().spliterator(), false)
            .filter(natjecaj -> natjecaj.getMobilnost() == null)
            .map(natjecajMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatjecajDTO> findOne(Long id) {
        log.debug("Request to get Natjecaj : {}", id);
        return natjecajRepository.findById(id).map(natjecajMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Natjecaj : {}", id);
        natjecajRepository.deleteById(id);
    }
}
