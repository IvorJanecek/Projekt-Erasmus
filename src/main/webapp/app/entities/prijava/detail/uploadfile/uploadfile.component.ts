import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IUploadFile } from '../../upload_files.model';
import { IPrijava } from '../../prijava.model';

@Component({
  selector: 'jhi-uploadfile',
  templateUrl: './uploadfile.component.html',
  styleUrls: ['./uploadfile.component.scss'],
})
export class UploadfileComponent implements OnInit {
  fileForm!: FormGroup;
  formData: FormData = new FormData();
  uploadFile: IUploadFile | null = null;
  prijava: Pick<IPrijava, 'id'> | null = null;

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
    console.log(this.prijava);
    if (this.uploadFile && this.prijava) {
      const url = `/api/uploadFile/${this.prijava}`;
      this.http.post(url, this.formData, { responseType: 'text' }).subscribe(
        response => {
          console.log(response);
          this.modal.dismiss();
        },
        error => console.error(error)
      );
    }
  }

  onFileSelect(event: any): void {
    if (event.target.files && event.target.files.length) {
      const files = event.target.files;
      for (let i = 0; i < files.length; i++) {
        this.formData.append('files', files[i]);
      }
    }
  }
}
