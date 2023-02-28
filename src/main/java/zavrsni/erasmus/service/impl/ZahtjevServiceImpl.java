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
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.repository.FakultetRepository;
import zavrsni.erasmus.repository.ZahtjevRepository;
import zavrsni.erasmus.service.ZahtjevService;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.ZahtjevDTO;
import zavrsni.erasmus.service.mapper.FakultetMapper;
import zavrsni.erasmus.service.mapper.ZahtjevMapper;

@Service
@Transactional
public class ZahtjevServiceImpl implements ZahtjevService {

    private final Logger log = LoggerFactory.getLogger(FakultetServiceImpl.class);

    private final ZahtjevRepository zahtjevRepository;

    private final ZahtjevMapper zahtjevMapper;

    public ZahtjevServiceImpl(ZahtjevRepository zahtjevRepository, ZahtjevMapper zahtjevMapper) {
        this.zahtjevRepository = zahtjevRepository;
        this.zahtjevMapper = zahtjevMapper;
    }

    @Override
    public ZahtjevDTO save(ZahtjevDTO zahtjevDTO) {
        log.debug("Request to save zahtjev : {}", zahtjevDTO);
        Zahtjev zahtjev = zahtjevMapper.toEntity(zahtjevDTO);
        zahtjev = zahtjevRepository.save(zahtjev);
        return zahtjevMapper.toDto(zahtjev);
    }

    @Override
    public ZahtjevDTO update(ZahtjevDTO zahtjevDTO) {
        log.debug("Request to update Zahtjev : {}", zahtjevDTO);
        Zahtjev zahtjev = zahtjevMapper.toEntity(zahtjevDTO);
        zahtjev = zahtjevRepository.save(zahtjev);
        return zahtjevMapper.toDto(zahtjev);
    }

    @Override
    public Optional<ZahtjevDTO> partialUpdate(ZahtjevDTO zahtjevDTO) {
        log.debug("Request to partially update Natjecaj : {}", zahtjevDTO);

        return zahtjevRepository
            .findById(zahtjevDTO.getId())
            .map(existingNatjecaj -> {
                zahtjevMapper.partialUpdate(existingNatjecaj, zahtjevDTO);

                return existingNatjecaj;
            })
            .map(zahtjevRepository::save)
            .map(zahtjevMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZahtjevDTO> findAll() {
        log.debug("Request to get all Zahtjevs");
        return zahtjevRepository.findAll().stream().map(zahtjevMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ZahtjevDTO> findOne(Long id) {
        log.debug("Request to get Zahtjev : {}", id);
        return zahtjevRepository.findById(id).map(zahtjevMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fakultet : {}", id);
        zahtjevRepository.deleteById(id);
    }
}
