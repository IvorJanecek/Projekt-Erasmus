package zavrsni.erasmus.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

public class UploadFileDTO implements Serializable {

    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    private PrijavaDTO prijava;

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
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public PrijavaDTO getPrijava() {
        return prijava;
    }

    public void setPrijava(PrijavaDTO prijava) {
        this.prijava = prijava;
    }
}
