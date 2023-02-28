package zavrsni.erasmus.service;

import java.util.List;
import java.util.Optional;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.dto.ZahtjevDTO;

public interface ZahtjevService {
    ZahtjevDTO save(ZahtjevDTO zahtjevDTO);

    ZahtjevDTO update(ZahtjevDTO zahtjevDTO);

    List<ZahtjevDTO> findAll();

    Optional<ZahtjevDTO> partialUpdate(ZahtjevDTO zahtjevDTO);

    Optional<ZahtjevDTO> findOne(Long id);

    void delete(Long id);
}
