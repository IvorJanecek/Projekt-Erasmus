package zavrsni.erasmus.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zavrsni.erasmus.domain.Fakultet;
import zavrsni.erasmus.repository.FakultetRepository;
import zavrsni.erasmus.service.FakultetService;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.mapper.FakultetMapper;

/**
 * Service Implementation for managing {@link Fakultet}.
 */
@Service
@Transactional
public class FakultetServiceImpl implements FakultetService {

    private final Logger log = LoggerFactory.getLogger(FakultetServiceImpl.class);

    private final FakultetRepository fakultetRepository;

    private final FakultetMapper fakultetMapper;

    public FakultetServiceImpl(FakultetRepository fakultetRepository, FakultetMapper fakultetMapper) {
        this.fakultetRepository = fakultetRepository;
        this.fakultetMapper = fakultetMapper;
    }

    @Override
    public FakultetDTO save(FakultetDTO fakultetDTO) {
        log.debug("Request to save Fakultet : {}", fakultetDTO);
        Fakultet fakultet = fakultetMapper.toEntity(fakultetDTO);
        fakultet = fakultetRepository.save(fakultet);
        return fakultetMapper.toDto(fakultet);
    }

    @Override
    public FakultetDTO update(FakultetDTO fakultetDTO) {
        log.debug("Request to update Fakultet : {}", fakultetDTO);
        Fakultet fakultet = fakultetMapper.toEntity(fakultetDTO);
        fakultet = fakultetRepository.save(fakultet);
        return fakultetMapper.toDto(fakultet);
    }

    @Override
    public Optional<FakultetDTO> partialUpdate(FakultetDTO fakultetDTO) {
        log.debug("Request to partially update Fakultet : {}", fakultetDTO);

        return fakultetRepository
            .findById(fakultetDTO.getId())
            .map(existingFakultet -> {
                fakultetMapper.partialUpdate(existingFakultet, fakultetDTO);

                return existingFakultet;
            })
            .map(fakultetRepository::save)
            .map(fakultetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FakultetDTO> findAll() {
        log.debug("Request to get all Fakultets");
        return fakultetRepository.findAll().stream().map(fakultetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FakultetDTO> findOne(Long id) {
        log.debug("Request to get Fakultet : {}", id);
        return fakultetRepository.findById(id).map(fakultetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fakultet : {}", id);
        fakultetRepository.deleteById(id);
    }
}
