package zavrsni.erasmus.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import zavrsni.erasmus.domain.Fakultet;
import zavrsni.erasmus.domain.Natjecaj;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.UploadFile;
import zavrsni.erasmus.domain.User;
import zavrsni.erasmus.domain.Zahtjev;
import zavrsni.erasmus.service.dto.FakultetDTO;
import zavrsni.erasmus.service.dto.NatjecajDTO;
import zavrsni.erasmus.service.dto.PrijavaDTO;
import zavrsni.erasmus.service.dto.UploadFileDTO;
import zavrsni.erasmus.service.dto.UserDTO;
import zavrsni.erasmus.service.dto.ZahtjevDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T19:33:15+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class PrijavaMapperImpl implements PrijavaMapper {

    @Override
    public Prijava toEntity(PrijavaDTO arg0) {
        if (arg0 == null) {
            return null;
        }

        Prijava prijava = new Prijava();

        prijava.setId(arg0.getId());
        prijava.setPrijavaName(arg0.getPrijavaName());
        prijava.setOpis(arg0.getOpis());
        prijava.setCreatedDate(arg0.getCreatedDate());
        prijava.setPrihvacen(arg0.getPrihvacen());
        prijava.setTrajanjeOd(arg0.getTrajanjeOd());
        prijava.setTrajanjeDo(arg0.getTrajanjeDo());
        byte[] data = arg0.getData();
        if (data != null) {
            prijava.setData(Arrays.copyOf(data, data.length));
        }
        prijava.setDataContentType(arg0.getDataContentType());
        prijava.setKategorija(arg0.getKategorija());
        prijava.user(userDTOToUser(arg0.getUser()));
        prijava.fakultet(fakultetDTOToFakultet(arg0.getFakultet()));
        prijava.natjecaj(natjecajDTOToNatjecaj(arg0.getNatjecaj()));

        return prijava;
    }

    @Override
    public List<Prijava> toEntity(List<PrijavaDTO> arg0) {
        if (arg0 == null) {
            return null;
        }

        List<Prijava> list = new ArrayList<Prijava>(arg0.size());
        for (PrijavaDTO prijavaDTO : arg0) {
            list.add(toEntity(prijavaDTO));
        }

        return list;
    }

    @Override
    public List<PrijavaDTO> toDto(List<Prijava> arg0) {
        if (arg0 == null) {
            return null;
        }

        List<PrijavaDTO> list = new ArrayList<PrijavaDTO>(arg0.size());
        for (Prijava prijava : arg0) {
            list.add(toDto(prijava));
        }

        return list;
    }

    @Override
    public void partialUpdate(Prijava arg0, PrijavaDTO arg1) {
        if (arg1 == null) {
            return;
        }

        if (arg1.getId() != null) {
            arg0.setId(arg1.getId());
        }
        if (arg1.getPrijavaName() != null) {
            arg0.setPrijavaName(arg1.getPrijavaName());
        }
        if (arg1.getOpis() != null) {
            arg0.setOpis(arg1.getOpis());
        }
        if (arg1.getCreatedDate() != null) {
            arg0.setCreatedDate(arg1.getCreatedDate());
        }
        if (arg1.getPrihvacen() != null) {
            arg0.setPrihvacen(arg1.getPrihvacen());
        }
        if (arg1.getTrajanjeOd() != null) {
            arg0.setTrajanjeOd(arg1.getTrajanjeOd());
        }
        if (arg1.getTrajanjeDo() != null) {
            arg0.setTrajanjeDo(arg1.getTrajanjeDo());
        }
        byte[] data = arg1.getData();
        if (data != null) {
            arg0.setData(Arrays.copyOf(data, data.length));
        }
        if (arg1.getDataContentType() != null) {
            arg0.setDataContentType(arg1.getDataContentType());
        }
        if (arg1.getKategorija() != null) {
            arg0.setKategorija(arg1.getKategorija());
        }
        if (arg1.getUser() != null) {
            if (arg0.getUser() == null) {
                arg0.user(new User());
            }
            userDTOToUser1(arg1.getUser(), arg0.getUser());
        }
        if (arg1.getFakultet() != null) {
            if (arg0.getFakultet() == null) {
                arg0.fakultet(new Fakultet());
            }
            fakultetDTOToFakultet1(arg1.getFakultet(), arg0.getFakultet());
        }
        if (arg1.getNatjecaj() != null) {
            if (arg0.getNatjecaj() == null) {
                arg0.natjecaj(new Natjecaj());
            }
            natjecajDTOToNatjecaj1(arg1.getNatjecaj(), arg0.getNatjecaj());
        }
    }

    @Override
    public PrijavaDTO toDto(Prijava s) {
        if (s == null) {
            return null;
        }

        PrijavaDTO prijavaDTO = new PrijavaDTO();

        prijavaDTO.setUser(toDtoUserId(s.getUser()));
        prijavaDTO.setFakultet(toDtoFakultetName(s.getFakultet()));
        prijavaDTO.setNatjecaj(toDtoNatjecajName(s.getNatjecaj()));
        prijavaDTO.setUploadFiles(uploadFileListToUploadFileDTOList(s.getFiles()));
        prijavaDTO.setId(s.getId());
        prijavaDTO.setPrijavaName(s.getPrijavaName());
        prijavaDTO.setOpis(s.getOpis());
        prijavaDTO.setCreatedDate(s.getCreatedDate());
        prijavaDTO.setPrihvacen(s.getPrihvacen());
        prijavaDTO.setTrajanjeOd(s.getTrajanjeOd());
        prijavaDTO.setTrajanjeDo(s.getTrajanjeDo());
        byte[] data = s.getData();
        if (data != null) {
            prijavaDTO.setData(Arrays.copyOf(data, data.length));
        }
        prijavaDTO.setDataContentType(s.getDataContentType());
        prijavaDTO.setKategorija(s.getKategorija());

        return prijavaDTO;
    }

    @Override
    public UserDTO toDtoUserId(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());

        return userDTO;
    }

    @Override
    public UploadFileDTO toUploadFile(UploadFile uploadFile) {
        if (uploadFile == null) {
            return null;
        }

        UploadFileDTO uploadFileDTO = new UploadFileDTO();

        uploadFileDTO.setId(uploadFile.getId());
        uploadFileDTO.setFileName(uploadFile.getFileName());
        uploadFileDTO.setFileType(uploadFile.getFileType());
        byte[] data = uploadFile.getData();
        if (data != null) {
            uploadFileDTO.setData(Arrays.copyOf(data, data.length));
        }
        uploadFileDTO.setPrijava(null);

        return uploadFileDTO;
    }

    @Override
    public FakultetDTO toDtoFakultetName(Fakultet fakultet) {
        if (fakultet == null) {
            return null;
        }

        FakultetDTO fakultetDTO = new FakultetDTO();

        fakultetDTO.setId(fakultet.getId());
        fakultetDTO.setName(fakultet.getName());

        return fakultetDTO;
    }

    @Override
    public NatjecajDTO toDtoNatjecajName(Natjecaj natjecaj) {
        if (natjecaj == null) {
            return null;
        }

        NatjecajDTO natjecajDTO = new NatjecajDTO();

        natjecajDTO.setId(natjecaj.getId());
        natjecajDTO.setName(natjecaj.getName());

        return natjecajDTO;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());

        return user;
    }

    protected Fakultet fakultetDTOToFakultet(FakultetDTO fakultetDTO) {
        if (fakultetDTO == null) {
            return null;
        }

        Fakultet fakultet = new Fakultet();

        fakultet.setId(fakultetDTO.getId());
        fakultet.setName(fakultetDTO.getName());
        fakultet.setZemlja(fakultetDTO.getZemlja());
        fakultet.setGrad(fakultetDTO.getGrad());
        fakultet.setAdresa(fakultetDTO.getAdresa());

        return fakultet;
    }

    protected Zahtjev zahtjevDTOToZahtjev(ZahtjevDTO zahtjevDTO) {
        if (zahtjevDTO == null) {
            return null;
        }

        Zahtjev zahtjev = new Zahtjev();

        zahtjev.setNatjecaj(natjecajDTOToNatjecaj(zahtjevDTO.getNatjecaj()));
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

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if (userDTO == null) {
            return;
        }

        if (userDTO.getId() != null) {
            mappingTarget.setId(userDTO.getId());
        }
        if (userDTO.getLogin() != null) {
            mappingTarget.setLogin(userDTO.getLogin());
        }
    }

    protected void fakultetDTOToFakultet1(FakultetDTO fakultetDTO, Fakultet mappingTarget) {
        if (fakultetDTO == null) {
            return;
        }

        if (fakultetDTO.getId() != null) {
            mappingTarget.setId(fakultetDTO.getId());
        }
        if (fakultetDTO.getName() != null) {
            mappingTarget.setName(fakultetDTO.getName());
        }
        if (fakultetDTO.getZemlja() != null) {
            mappingTarget.setZemlja(fakultetDTO.getZemlja());
        }
        if (fakultetDTO.getGrad() != null) {
            mappingTarget.setGrad(fakultetDTO.getGrad());
        }
        if (fakultetDTO.getAdresa() != null) {
            mappingTarget.setAdresa(fakultetDTO.getAdresa());
        }
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

    protected List<UploadFileDTO> uploadFileListToUploadFileDTOList(List<UploadFile> list) {
        if (list == null) {
            return null;
        }

        List<UploadFileDTO> list1 = new ArrayList<UploadFileDTO>(list.size());
        for (UploadFile uploadFile : list) {
            list1.add(toUploadFile(uploadFile));
        }

        return list1;
    }
}
