package zavrsni.erasmus.web.rest;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.Prijava;
import zavrsni.erasmus.domain.UploadFile;
import zavrsni.erasmus.repository.MobilnostRepository;
import zavrsni.erasmus.repository.PrijavaRepository;
import zavrsni.erasmus.repository.UploadFilesRepository;

@RestController
@RequestMapping("/api")
public class FileResource {

    private final PrijavaRepository prijavaRepository;

    private final UploadFilesRepository uploadFilesRepository;
    private final MobilnostRepository mobilnostRepository;

    public FileResource(
        PrijavaRepository prijavaRepository,
        UploadFilesRepository uploadFilesRepository,
        MobilnostRepository mobilnostRepository
    ) {
        this.prijavaRepository = prijavaRepository;
        this.uploadFilesRepository = uploadFilesRepository;
        this.mobilnostRepository = mobilnostRepository;
    }

    @PostMapping("/uploadFiles/{prijavaId}")
    public ResponseEntity<String> uploadFiles(@PathVariable Long prijavaId, @RequestParam("files") List<MultipartFile> files) {
        try {
            Prijava prijava = prijavaRepository
                .findById(prijavaId)
                .orElseThrow(() -> new RuntimeException("Prijava with ID " + prijavaId + " not found"));

            for (MultipartFile file : files) {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setFileType(file.getContentType());
                uploadFile.setData(file.getBytes());
                uploadFile.setPrijava(prijava);
                uploadFilesRepository.save(uploadFile);
            }

            prijavaRepository.save(prijava);

            return ResponseEntity.ok("Files uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files: " + e.getMessage());
        }
    }

    @PostMapping("/uploadFiles/mobilnost/{mobilnostId}")
    public ResponseEntity<String> uploadFilesMobilnost(@PathVariable Long mobilnostId, @RequestParam("files") List<MultipartFile> files) {
        try {
            Mobilnost mobilnost = mobilnostRepository
                .findById(mobilnostId)
                .orElseThrow(() -> new RuntimeException("Prijava with ID " + mobilnostId + " not found"));

            for (MultipartFile file : files) {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setFileType(file.getContentType());
                uploadFile.setData(file.getBytes());
                uploadFile.setMobilnost(mobilnost);
                uploadFilesRepository.save(uploadFile);
            }

            mobilnostRepository.save(mobilnost);

            return ResponseEntity.ok("Files uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files: " + e.getMessage());
        }
    }
}
