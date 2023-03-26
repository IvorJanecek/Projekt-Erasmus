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
    date = "2023-03-22T22:50:21+0100",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class ZahtjevMapperImpl implements ZahtjevMapper {

    @Override
    public Zahtjev toEntity(ZahtjevDTO dto) {
        if (dto == null) {
            return null;
        }

        Zahtjev zahtjev = new Zahtjev();

        zahtjev.setNatjecaj(natjecajDTOToNatjecaj(dto.getNatjecaj()));
        zahtjev.setName(dto.getName());
        zahtjev.id(dto.getId());

        return zahtjev;
    }

    @Override
    public ZahtjevDTO toDto(Zahtjev entity) {
        if (entity == null) {
            return null;
        }

        ZahtjevDTO zahtjevDTO = new ZahtjevDTO();

        zahtjevDTO.setId(entity.getId());
        zahtjevDTO.setName(entity.getName());
        zahtjevDTO.setNatjecaj(null);

        return zahtjevDTO;
    }

    @Override
    public List<Zahtjev> toEntity(List<ZahtjevDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Zahtjev> list = new ArrayList<Zahtjev>(dtoList.size());
        for (ZahtjevDTO zahtjevDTO : dtoList) {
            list.add(toEntity(zahtjevDTO));
        }

        return list;
    }

    @Override
    public List<ZahtjevDTO> toDto(List<Zahtjev> entityList) {
        if (entityList == null) {
            return null;
        }

        List<ZahtjevDTO> list = new ArrayList<ZahtjevDTO>(entityList.size());
        for (Zahtjev zahtjev : entityList) {
            list.add(toDto(zahtjev));
        }

        return list;
    }

    @Override
    public void partialUpdate(Zahtjev entity, ZahtjevDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getNatjecaj() != null) {
            if (entity.getNatjecaj() == null) {
                entity.setNatjecaj(new Natjecaj());
            }
            natjecajDTOToNatjecaj1(dto.getNatjecaj(), entity.getNatjecaj());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getId() != null) {
            entity.id(dto.getId());
        }
    }

    protected Set<Zahtjev> zahtjevDTOListToZahtjevSet(List<ZahtjevDTO> list) {
        if (list == null) {
            return null;
        }

        Set<Zahtjev> set = new LinkedHashSet<Zahtjev>(Math.max((int) (list.size() / .75f) + 1, 16));
        for (ZahtjevDTO zahtjevDTO : list) {
            set.add(toEntity(zahtjevDTO));
        }

        return set;
    }

    protected Natjecaj natjecajDTOToNatjecaj(NatjecajDTO natjecajDTO) {
        if (natjecajDTO == null) {
            return null;
        }

        Natjecaj natjecaj = new Natjecaj();

        natjecaj.setZahtjevs(zahtjevDTOListToZahtjevSet(natjecajDTO.getZahtjevs()));
        natjecaj.setId(natjecajDTO.getId());
        natjecaj.setName(natjecajDTO.getName());
        natjecaj.setDescription(natjecajDTO.getDescription());
        natjecaj.setCreateDate(natjecajDTO.getCreateDate());
        natjecaj.setDatumOd(natjecajDTO.getDatumOd());
        natjecaj.setDatumDo(natjecajDTO.getDatumDo());
        natjecaj.setStatus(natjecajDTO.getStatus());

        return natjecaj;
    }

    protected List<ZahtjevDTO> zahtjevSetToZahtjevDTOList(Set<Zahtjev> set) {
        if (set == null) {
            return null;
        }

        List<ZahtjevDTO> list = new ArrayList<ZahtjevDTO>(set.size());
        for (Zahtjev zahtjev : set) {
            list.add(toDto(zahtjev));
        }

        return list;
    }

    protected NatjecajDTO natjecajToNatjecajDTO(Natjecaj natjecaj) {
        if (natjecaj == null) {
            return null;
        }

        NatjecajDTO natjecajDTO = new NatjecajDTO();

        natjecajDTO.setId(natjecaj.getId());
        natjecajDTO.setName(natjecaj.getName());
        natjecajDTO.setZahtjevs(zahtjevSetToZahtjevDTOList(natjecaj.getZahtjevs()));
        natjecajDTO.setDescription(natjecaj.getDescription());
        natjecajDTO.setCreateDate(natjecaj.getCreateDate());
        natjecajDTO.setDatumOd(natjecaj.getDatumOd());
        natjecajDTO.setDatumDo(natjecaj.getDatumDo());
        natjecajDTO.setStatus(natjecaj.getStatus());

        return natjecajDTO;
    }

    protected void natjecajDTOToNatjecaj1(NatjecajDTO natjecajDTO, Natjecaj mappingTarget) {
        if (natjecajDTO == null) {
            return;
        }

        if (mappingTarget.getZahtjevs() != null) {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(natjecajDTO.getZahtjevs());
            if (set != null) {
                mappingTarget.getZahtjevs().clear();
                mappingTarget.getZahtjevs().addAll(set);
            }
        } else {
            Set<Zahtjev> set = zahtjevDTOListToZahtjevSet(natjecajDTO.getZahtjevs());
            if (set != null) {
                mappingTarget.setZahtjevs(set);
            }
        }
        if (natjecajDTO.getId() != null) {
            mappingTarget.setId(natjecajDTO.getId());
        }
        if (natjecajDTO.getName() != null) {
            mappingTarget.setName(natjecajDTO.getName());
        }
        if (natjecajDTO.getDescription() != null) {
            mappingTarget.setDescription(natjecajDTO.getDescription());
        }
        if (natjecajDTO.getCreateDate() != null) {
            mappingTarget.setCreateDate(natjecajDTO.getCreateDate());
        }
        if (natjecajDTO.getDatumOd() != null) {
            mappingTarget.setDatumOd(natjecajDTO.getDatumOd());
        }
        if (natjecajDTO.getDatumDo() != null) {
            mappingTarget.setDatumDo(natjecajDTO.getDatumDo());
        }
        if (natjecajDTO.getStatus() != null) {
            mappingTarget.setStatus(natjecajDTO.getStatus());
        }
    }
}
