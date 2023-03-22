package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

public class FileDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] data;

    private String dataContentType;

    private NatjecajDTO natjecaj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public NatjecajDTO getNatjecaj() {
        return natjecaj;
    }

    public void setNatjecaj(NatjecajDTO natjecaj) {
        this.natjecaj = natjecaj;
    }

    public FileDTO() {}

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
