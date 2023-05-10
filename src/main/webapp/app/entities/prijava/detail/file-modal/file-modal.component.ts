import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { IUploadFile } from '../../upload_files.model';

@Component({
  selector: 'jhi-file-modal',
  templateUrl: './file-modal.component.html',
  styleUrls: ['./file-modal.component.scss'],
})
export class FileModalComponent implements OnInit {
  fileForm!: FormGroup;
  formData: FormData = new FormData();
  uploadFile: IUploadFile | null = null;

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
    if (event.target.files && event.target.files.length) {
      const file = event.target.files[0];
      this.formData.append('file', file, file.name);
    }
  }
}
