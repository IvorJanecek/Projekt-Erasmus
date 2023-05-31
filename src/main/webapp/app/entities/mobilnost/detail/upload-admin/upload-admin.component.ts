import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IMobilnost } from '../../mobilnost.model';

@Component({
  selector: 'jhi-upload-admin',
  templateUrl: './upload-admin.component.html',
  styleUrls: ['./upload-admin.component.scss'],
})
export class UploadAdminComponent implements OnInit {
  fileForm!: FormGroup;
  selectedFiles!: FileList;
  formData: FormData = new FormData();
  mobilnost: IMobilnost | null = null;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.fileForm = this.formBuilder.group({
      files: ['', Validators.required],
    });
    this.mobilnost = history.state.mobilnost;
  }

  addFiles(event: Event): void {
    this.formData = new FormData();
    const target = event.target as HTMLInputElement;
    if (target.files !== null) {
      this.selectedFiles = target.files;
    } else {
      console.log('No files selected!');
      return;
    }

    for (let i = 0; i < this.selectedFiles.length; i++) {
      this.formData.append('files', this.selectedFiles[i], this.selectedFiles[i].name);
    }

    console.log(this.mobilnost);
  }

  uploadFiles(): void {
    const mobilnostId = this.mobilnost; // Replace with the ID of the Prijava instance
    this.http.post(`/api/uploadFilesAdmin/mobilnost/${mobilnostId}`, this.formData).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
  }
}
