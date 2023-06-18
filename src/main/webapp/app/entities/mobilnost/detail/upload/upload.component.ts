import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IMobilnost } from '../../mobilnost.model';

@Component({
  selector: 'jhi-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss'],
})
export class UploadComponent implements OnInit {
  fileForm!: FormGroup;
  selectedFiles!: FileList;
  formData: FormData = new FormData();
  mobilnost: IMobilnost | null = null;

  constructor(private formBuilder: FormBuilder, private router: Router, private http: HttpClient, private route: ActivatedRoute) {}

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
    this.http.post(`/api/uploadFiles/mobilnost/${mobilnostId}`, this.formData, { responseType: 'text' }).subscribe(
      response => {
        console.log(response);
        this.router.navigate([`/mobilnost/${mobilnostId}/view`]);
      },
      error => console.error(error)
    );
  }
}
