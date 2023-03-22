package zavrsni.erasmus.service.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.ZahtjevDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-14T22:55:32+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class NatjecajMapperImpl implements NatjecajMapper {

    @Override
    public Natjecaj toEntity(NatjecajDTO arg0) {
        if (arg0 == null) {
            return null;
        }

        Natjecaj natjecaj = new Natjecaj();

        natjecaj.setZahtjevs(zahtjevDTOListToZahtjevSet(arg0.getZahtjevs()));
        natjecaj.setId(arg0.getId());
        natjecaj.setName(arg0.getName());
        natjecaj.setDescription(arg0.getDescription());
        natjecaj.setCreateDate(arg0.getCreateDate());
        natjecaj.setDatumOd(arg0.getDatumOd());
        natjecaj.setDatumDo(arg0.getDatumDo());
        natjecaj.setStatus(arg0.getStatus());

        return natjecaj;
    }

    @Override
    public NatjecajDTO toDto(Natjecaj arg0) {
        if (arg0 == null) {
            return null;
        }

        NatjecajDTO natjecajDTO = new NatjecajDTO();

        natjecajDTO.setId(arg0.getId());
        natjecajDTO.setName(arg0.getName());
        natjecajDTO.setZahtjevs(zahtjevSetToZahtjevDTOList(arg0.getZahtjevs()));
        natjecajDTO.setDescription(arg0.getDescription());
        natjecajDTO.setCreateDate(arg0.getCreateDate());
        natjecajDTO.setDatumOd(arg0.getDatumOd());
        natjecajDTO.setDatumDo(arg0.getDatumDo());
        natjecajDTO.setStatus(arg0.getStatus());

        return natjecajDTO;
    }

    @Override
    public List<Natjecaj> toEntity(List<NatjecajDTO> arg0) {
        if (arg0 == null) {
            return null;
        }

        List<Natjecaj> list = new ArrayList<Natjecaj>(arg0.size());
        for (NatjecajDTO natjecajDTO : arg0) {
            list.add(toEntity(natjecajDTO));
        }

        return list;
    }

    @Override
    public List<NatjecajDTO> toDto(List<Natjecaj> arg0) {
        if (arg0 == null) {
            return null;
        }

        List<NatjecajDTO> list = new ArrayList<NatjecajDTO>(arg0.size());
        for (Natjecaj natjecaj : arg0) {
            list.add(toDto(natjecaj));
        }

        return list;
    }

    @Override
    public void partialUpdate(Natjecaj arg0, NatjecajDTO arg1) {
        if (arg1 == null) {
            return;
        }

        if (arg0.getZahtjevs() != null) {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(arg1.getZahtjevs());
            if (set != null) {
                arg0.getZahtjevs().clear();
                arg0.getZahtjevs().addAll(set);
            }
        } else {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(arg1.getZahtjevs());
            if (set != null) {
                arg0.setZahtjevs(set);
            }
        }
        if (arg1.getId() != null) {
            arg0.setId(arg1.getId());
        }
        if (arg1.getName() != null) {
            arg0.setName(arg1.getName());
        }
        if (arg1.getDescription() != null) {
            arg0.setDescription(arg1.getDescription());
        }
        if (arg1.getCreateDate() != null) {
            arg0.setCreateDate(arg1.getCreateDate());
        }
        if (arg1.getDatumOd() != null) {
            arg0.setDatumOd(arg1.getDatumOd());
        }
        if (arg1.getDatumDo() != null) {
            arg0.setDatumDo(arg1.getDatumDo());
        }
        if (arg1.getStatus() != null) {
            arg0.setStatus(arg1.getStatus());
        }
    }

    protected Zahtjev zahtjevDTOToZahtjev(ZahtjevDTO zahtjevDTO) {
        if (zahtjevDTO == null) {
            return null;
        }

        Zahtjev zahtjev = new Zahtjev();

        zahtjev.setNatjecaj(toEntity(zahtjevDTO.getNatjecaj()));
        zahtjev.setName(zahtjevDTO.getName());
        zahtjev.id(zahtjevDTO.getId());

        return zahtjev;
    }

    protected Set<Zahtjev> zahtjevDTOListToZahtjevSet(List<ZahtjevDTO> list) {
        if (list == null) {
            return null;
        }

        Set<Zahtjev> set = new LinkedHashSet<Zahtjev>(Math.max((int) (list.size() / .75f) + 1, 16));
        for (ZahtjevDTO zahtjevDTO : list) {
            set.add(zahtjevDTOToZahtjev(zahtjevDTO));
        }

        return set;
    }

    protected ZahtjevDTO zahtjevToZahtjevDTO(Zahtjev zahtjev) {
        if (zahtjev == null) {
            return null;
        }

        ZahtjevDTO zahtjevDTO = new ZahtjevDTO();

        zahtjevDTO.setId(zahtjev.getId());
        zahtjevDTO.setName(zahtjev.getName());
        zahtjevDTO.setNatjecaj(null);

        return zahtjevDTO;
    }

    protected List<ZahtjevDTO> zahtjevSetToZahtjevDTOList(Set<Zahtjev> set) {
        if (set == null) {
            return null;
        }

        List<ZahtjevDTO> list = new ArrayList<ZahtjevDTO>(set.size());
        for (Zahtjev zahtjev : set) {
            list.add(zahtjevToZahtjevDTO(zahtjev));
        }

        return list;
    }
}
