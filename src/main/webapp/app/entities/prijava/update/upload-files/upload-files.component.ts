import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { ZahtjevService } from '../../../zahtjev/service/zahtjev.service';
import { IZahtjev } from '../../../zahtjev/zatjev.model';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
})
export class UploadFilesComponent implements OnInit {
  fileForm!: FormGroup;
  selectedFiles: File[][] = [];
  formData: FormData[] = [];
  natjecaj: INatjecaj | any;
  zahtjevs: IZahtjev[] | null = [];
  fileNames: string[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute,
    private zahtjevService: ZahtjevService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fileForm = this.formBuilder.group({
      files: ['', Validators.required],
    });
    this.natjecaj = history.state.natjecaj;
    this.zahtjevService.findAllByNatjecajId(this.natjecaj.id).subscribe(result => {
      this.zahtjevs = result.body;
    });
    this.fileForm.get('files')!.disable();
  }

  onFileSelect(event: Event, column: number): void {
    const target = event.target as HTMLInputElement;
    if (target.files !== null) {
      const files = Array.from(target.files); // Convert FileList to array
      if (!Array.isArray(this.selectedFiles[column])) {
        this.selectedFiles[column] = []; // Initialize as empty array if not already
      }
      this.selectedFiles[column] = [...this.selectedFiles[column], ...files];
      // Rest of the code...
      if (target.files !== null) {
        this.selectedFiles[column] = [...this.selectedFiles[column], target.files[0]];
        this.formData[column] = new FormData();
        this.formData[column].append('files', target.files[0], target.files[0].name);
        this.fileNames[column] = ''; // Update with an empty string
      }
    } else {
      console.log('No file selected!');
    }

    const allFilesSelected = this.selectedFiles.every(files => files.length > 0);
    if (allFilesSelected) {
      this.fileForm.get('files')!.enable(); // Enable the button
    } else {
      this.fileForm.get('files')!.disable(); // Disable the button
    }
  }

  uploadFiles(): void {
    const prijavaId = this.route.snapshot.params.prijavaId; // Replace with the ID of the Prijava instance
    if (this.selectedFiles.every(files => files.length > 0)) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.http.post(`/api/uploadFiles/${prijavaId}`, this.formData[i], { responseType: 'text' }).subscribe(
          response => console.log(response),
          error => console.error(error)
        );
      }
      this.router.navigate(['/prijava']);
    } else {
      console.log('Please select a file for each row!');
    }
  }
}
