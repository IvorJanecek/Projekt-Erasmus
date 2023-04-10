package zavrsni.erasmus.domain;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prijava_id", referencedColumnName = "id")
    private Prijava prijava;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobilnost_id", referencedColumnName = "id")
    private Mobilnost mobilnost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return this.data;
    }

    public UploadFile data(byte[] data) {
        this.setData(data);
        return this;
    }

    public Mobilnost getMobilnost() {
        return mobilnost;
    }

    public void setMobilnost(Mobilnost mobilnost) {
        this.mobilnost = mobilnost;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Prijava getPrijava() {
        return prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }
}
