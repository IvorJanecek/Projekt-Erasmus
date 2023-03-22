import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
})
export class UploadFilesComponent implements OnInit {
  fileForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {}

  ngOnInit(): void {
    this.fileForm = this.formBuilder.group({
      files: ['', Validators.required],
    });
  }

  onSubmit() {
    const formData = new FormData();
    const files = this.fileForm.get('files')!.value;

    if (!files || files.length === 0) {
      console.log('No files selected');
      return;
    }

    for (let i = 0; i < files.length; i++) {
      if (typeof files[i] !== 'object') {
        console.log(`Skipping invalid file: ${files[i]}`);
        continue;
      }
      formData.append('files', files[i], files[i].name);
      console.log('File:', files[i].name);
    }

    const prijavaId = 1; // Replace with the ID of the Prijava instance
    this.http.post(`/api/uploadFiles/${prijavaId}`, formData).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
  }
}
