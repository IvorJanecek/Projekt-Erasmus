package zavrsni.erasmus.domain;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class File {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    public File(byte[] data, String dataContentType, Prijava prijava) {
        this.data = data;
        this.dataContentType = dataContentType;
        this.prijava = prijava;
    }

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @ManyToOne
    private Prijava prijava;

    public byte[] getData() {
        return this.data;
    }

    public File data(byte[] data) {
        this.setData(data);
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return this.dataContentType;
    }

    public File dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Prijava getPrijava() {
        return prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }
}
