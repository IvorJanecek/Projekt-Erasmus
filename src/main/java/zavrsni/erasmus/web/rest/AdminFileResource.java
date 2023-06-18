package zavrsni.erasmus.web.rest;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zavrsni.erasmus.domain.Mobilnost;
import zavrsni.erasmus.domain.UploadFileAdmin;
import zavrsni.erasmus.repository.MobilnostRepository;
import zavrsni.erasmus.repository.UploadFilesAdminRepository;

@RestController
@RequestMapping("/api")
public class AdminFileResource {

    private final UploadFilesAdminRepository uploadFilesAdminRepository;
    private final MobilnostRepository mobilnostRepository;

    public AdminFileResource(UploadFilesAdminRepository uploadFilesAdminRepository, MobilnostRepository mobilnostRepository) {
        this.uploadFilesAdminRepository = uploadFilesAdminRepository;
        this.mobilnostRepository = mobilnostRepository;
    }

    @PatchMapping("/uploadFileAdmin/{fileId}")
    public ResponseEntity<String> uploadFileAdmin(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) {
        try {
            UploadFileAdmin uploadFileAdmin = uploadFilesAdminRepository
                .findById(fileId)
                .orElseThrow(() -> new RuntimeException("File with ID " + fileId + " not found"));

            uploadFileAdmin.setFileName(file.getOriginalFilename());
            uploadFileAdmin.setFileType(file.getContentType());
            uploadFileAdmin.setData(file.getBytes());
            uploadFilesAdminRepository.save(uploadFileAdmin);

            return ResponseEntity.ok("File edited successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit file " + e.getMessage());
        }
    }

    @GetMapping("/uploadFilesAdmin/mobilnost/{mobilnostId}")
    public List<UploadFileAdmin> getUploadFilesAdminByMobilnostId(@PathVariable Long mobilnostId) {
        Mobilnost mobilnost = mobilnostRepository
            .findById(mobilnostId)
            .orElseThrow(() -> new RuntimeException("Mobilnost with ID " + mobilnostId + " not found"));

        List<UploadFileAdmin> uploadFilesAdmin = uploadFilesAdminRepository.findByMobilnost(mobilnost);

        return uploadFilesAdmin;
    }

    @PostMapping("/uploadFilesAdmin/mobilnost/{mobilnostId}")
    public ResponseEntity<String> uploadFilesAdminMobilnost(
        @PathVariable Long mobilnostId,
        @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            Mobilnost mobilnost = mobilnostRepository
                .findById(mobilnostId)
                .orElseThrow(() -> new RuntimeException("Prijava sa ID " + mobilnostId + " nije pronađena"));
            for (MultipartFile file : files) {
                UploadFileAdmin uploadFileAdmin = new UploadFileAdmin();
                uploadFileAdmin.setFileName(file.getOriginalFilename());
                uploadFileAdmin.setFileType(file.getContentType());
                uploadFileAdmin.setData(file.getBytes());
                uploadFileAdmin.setMobilnost(mobilnost);
                uploadFilesAdminRepository.save(uploadFileAdmin);
            }

            mobilnostRepository.save(mobilnost);

            return ResponseEntity.ok("Dokument uspješno uploadan");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nije uspjelo uploadat dokumente: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteFileAdmin/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        try {
            UploadFileAdmin uploadFileAdmin = uploadFilesAdminRepository
                .findById(fileId)
                .orElseThrow(() -> new RuntimeException("Dokument sa ID " + fileId + " nije pronađen"));

            uploadFilesAdminRepository.delete(uploadFileAdmin);

            return ResponseEntity.ok("Dokumentu uspješno obrisan");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nije uspijelo obrisati dokument: " + e.getMessage());
        }
    }
}
