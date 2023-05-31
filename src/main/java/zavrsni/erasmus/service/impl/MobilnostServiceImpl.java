package zavrsni.erasmus.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.enumeration.StatusMobilnosti;
import zavrsni.erasmus.repository.MobilnostRepository;
import zavrsni.erasmus.service.MobilnostService;
import zavrsni.erasmus.service.dto.MobilnostDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.mapper.MobilnostMapper;

/**
 * Service Implementation for managing {@link Mobilnost}.
 */
@Service
@Transactional
public class MobilnostServiceImpl implements MobilnostService {

    private final Logger log = LoggerFactory.getLogger(MobilnostServiceImpl.class);

    private final MobilnostRepository mobilnostRepository;

    private final MobilnostMapper mobilnostMapper;

    public MobilnostServiceImpl(MobilnostRepository mobilnostRepository, MobilnostMapper mobilnostMapper) {
        this.mobilnostRepository = mobilnostRepository;
        this.mobilnostMapper = mobilnostMapper;
    }

    @Override
    public MobilnostDTO save(MobilnostDTO mobilnostDTO) {
        log.debug("Request to save Mobilnost : {}", mobilnostDTO);
        Mobilnost mobilnost = mobilnostMapper.toEntity(mobilnostDTO);
        if (mobilnost.getId() == null) {
            mobilnost.setStatusMobilnosti(StatusMobilnosti.OTVORENA);
        }
        mobilnost = mobilnostRepository.save(mobilnost);
        return mobilnostMapper.toDto(mobilnost);
    }

    @Override
    public MobilnostDTO update(MobilnostDTO mobilnostDTO) {
        log.debug("Request to update Mobilnost : {}", mobilnostDTO);
        Mobilnost mobilnost = mobilnostMapper.toEntity(mobilnostDTO);
        mobilnost = mobilnostRepository.save(mobilnost);
        return mobilnostMapper.toDto(mobilnost);
    }

    @Override
    public Optional<MobilnostDTO> partialUpdate(MobilnostDTO mobilnostDTO) {
        log.debug("Request to partially update Mobilnost : {}", mobilnostDTO);

        return mobilnostRepository
            .findById(mobilnostDTO.getId())
            .map(existingMobilnost -> {
                mobilnostMapper.partialUpdate(existingMobilnost, mobilnostDTO);

                return existingMobilnost;
            })
            .map(mobilnostRepository::save)
            .map(mobilnostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MobilnostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mobilnosts");
        return mobilnostRepository.findAll(pageable).map(mobilnostMapper::toDto);
    }

    public Page<MobilnostDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mobilnostRepository.findAllWithEagerRelationships(pageable).map(mobilnostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MobilnostDTO> findOne(Long id) {
        log.debug("Request to get Mobilnost : {}", id);
        return mobilnostRepository.findOneWithEagerRelationships(id).map(mobilnostMapper::toDto);
    }

    @Override
    public Page<MobilnostDTO> findByUserIsCurrentUser(Pageable pageable) {
        return mobilnostRepository.findByUserIsCurrentUserOrAdmin(pageable).map(mobilnostMapper::toDto);
    }

    @Override
    public Page<MobilnostDTO> findByUserIsCurrentUserWithEagerRelationships(Pageable pageable) {
        return mobilnostRepository.findAllWithEagerRelationshipsForCurrentUser(pageable).map(mobilnostMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mobilnost : {}", id);
        mobilnostRepository.deleteById(id);
    }
}
