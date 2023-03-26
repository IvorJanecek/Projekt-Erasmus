import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
})
export class UploadFilesComponent implements OnInit {
  fileForm!: FormGroup;
  selectedFiles!: FileList;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.fileForm = this.formBuilder.group({
      files: ['', Validators.required],
    });
    const prijavaId = this.route.snapshot.params.prijavaId;
  }

  uploadFiles(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files !== null) {
      this.selectedFiles = target.files;
    } else {
      console.log('No files selected!');
      return;
    }
    const formData = new FormData();

    for (let i = 0; i < this.selectedFiles.length; i++) {
      formData.append('files', this.selectedFiles[i], this.selectedFiles[i].name);
    }

    const prijavaId = this.route.snapshot.params.prijavaId; // Replace with the ID of the Prijava instance
    this.http.post(`/api/uploadFiles/${prijavaId}`, formData).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
  }
}
