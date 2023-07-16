import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IUploadFile } from 'app/entities/prijava/upload_files.model';

@Component({
  selector: 'jhi-file-modal',
  templateUrl: './file-modal.component.html',
  styleUrls: ['./file-modal.component.scss'],
})
export class FileModalComponent implements OnInit {
  fileForm!: FormGroup;
  formData: FormData = new FormData();
  uploadFile: IUploadFile | null = null;
  errorMessage = '';

  constructor(
    public modal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.fileForm = this.formBuilder.group({
      file: ['', Validators.required],
    });
  }

  editFile() {
    console.log(this.uploadFile?.id);
    this.http.patch(`/api/uploadFile/${this.uploadFile?.id}`, this.formData, { responseType: 'text' }).subscribe(
      response => {
        console.log(response);
        this.modal.dismiss();
      },
      error => console.error(error)
    );
  }

  onFileSelect(event: any): void {
    const file = event.target.files[0];
    const fileSizeInBytes = file.size;
    const maxSizeInBytes = 1048576; // 1 MB in bytes

    if (fileSizeInBytes > maxSizeInBytes) {
      this.errorMessage = 'Dokument je veći od 1MB!';
      this.fileForm.get('file')?.setValue(null); // Clear the file input value
      return;
    }

    this.formData.set('file', file, file.name);
  }
}
