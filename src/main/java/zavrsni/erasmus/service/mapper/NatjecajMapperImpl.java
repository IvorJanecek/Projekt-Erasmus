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
    date = "2023-04-23T16:54:11+0200",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class NatjecajMapperImpl implements NatjecajMapper {

    @Override
    public Natjecaj toEntity(NatjecajDTO dto) {
        if (dto == null) {
            return null;
        }

        Natjecaj natjecaj = new Natjecaj();

        natjecaj.setZahtjevs(zahtjevDTOListToZahtjevSet(dto.getZahtjevs()));
        natjecaj.setId(dto.getId());
        natjecaj.setName(dto.getName());
        natjecaj.setDescription(dto.getDescription());
        natjecaj.setCreateDate(dto.getCreateDate());
        natjecaj.setDatumOd(dto.getDatumOd());
        natjecaj.setDatumDo(dto.getDatumDo());
        natjecaj.setStatus(dto.getStatus());
        natjecaj.setKorisnik(dto.getKorisnik());

        return natjecaj;
    }

    @Override
    public NatjecajDTO toDto(Natjecaj entity) {
        if (entity == null) {
            return null;
        }

        NatjecajDTO natjecajDTO = new NatjecajDTO();

        natjecajDTO.setId(entity.getId());
        natjecajDTO.setName(entity.getName());
        natjecajDTO.setZahtjevs(zahtjevSetToZahtjevDTOList(entity.getZahtjevs()));
        natjecajDTO.setDescription(entity.getDescription());
        natjecajDTO.setCreateDate(entity.getCreateDate());
        natjecajDTO.setDatumOd(entity.getDatumOd());
        natjecajDTO.setDatumDo(entity.getDatumDo());
        natjecajDTO.setStatus(entity.getStatus());
        natjecajDTO.setKorisnik(entity.getKorisnik());

        return natjecajDTO;
    }

    @Override
    public List<Natjecaj> toEntity(List<NatjecajDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Natjecaj> list = new ArrayList<Natjecaj>(dtoList.size());
        for (NatjecajDTO natjecajDTO : dtoList) {
            list.add(toEntity(natjecajDTO));
        }

        return list;
    }

    @Override
    public List<NatjecajDTO> toDto(List<Natjecaj> entityList) {
        if (entityList == null) {
            return null;
        }

        List<NatjecajDTO> list = new ArrayList<NatjecajDTO>(entityList.size());
        for (Natjecaj natjecaj : entityList) {
            list.add(toDto(natjecaj));
        }

        return list;
    }

    @Override
    public void partialUpdate(Natjecaj entity, NatjecajDTO dto) {
        if (dto == null) {
            return;
        }

        if (entity.getZahtjevs() != null) {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(dto.getZahtjevs());
            if (set != null) {
                entity.getZahtjevs().clear();
                entity.getZahtjevs().addAll(set);
            }
        } else {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(dto.getZahtjevs());
            if (set != null) {
                entity.setZahtjevs(set);
            }
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCreateDate() != null) {
            entity.setCreateDate(dto.getCreateDate());
        }
        if (dto.getDatumOd() != null) {
            entity.setDatumOd(dto.getDatumOd());
        }
        if (dto.getDatumDo() != null) {
            entity.setDatumDo(dto.getDatumDo());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getKorisnik() != null) {
            entity.setKorisnik(dto.getKorisnik());
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
