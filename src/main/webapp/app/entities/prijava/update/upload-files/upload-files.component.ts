import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { ZahtjevService } from '../../../zahtjev/service/zahtjev.service';
import { IZahtjev } from '../../../zahtjev/zatjev.model';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
})
export class UploadFilesComponent implements OnInit {
  fileForm!: FormGroup;
  selectedFiles: File[][] = [];
  formData: FormData[][] = [];
  natjecaj: INatjecaj | any;
  zahtjevs: IZahtjev[] | null = [];
  fileNames: string[] = [];
  errorMessage = '';

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
      if (!Array.isArray(this.formData[column])) {
        this.formData[column] = []; // Initialize as empty array if not already
      }
      this.formData[column] = [...this.formData[column], new FormData()]; // Create a new FormData object for each file
      this.formData[column][this.formData[column].length - 1].append('files', files[0], files[0].name); // Append the file to the last FormData object
      this.fileNames[column] = ''; // Update with an empty string
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

    // Check if all rows have selected files
    const allFilesSelected = this.selectedFiles.length === this.zahtjevs?.length && this.selectedFiles.every(files => files.length > 0);

    if (allFilesSelected) {
      // Create an array to store the observables for each file upload request
      const uploadRequests: Observable<any>[] = [];

      // Iterate over the selected files and create upload requests
      for (let i = 0; i < this.selectedFiles.length; i++) {
        for (let j = 0; j < this.selectedFiles[i].length; j++) {
          const file = this.selectedFiles[i][j];
          const formData = this.formData[i][j];

          // Create an upload request for each file
          const uploadRequest = this.http.post(`/api/uploadFiles/${prijavaId}`, formData, { responseType: 'text' });

          uploadRequests.push(uploadRequest);
        }
      }

      forkJoin(uploadRequests).subscribe(response => {
        this.router.navigate(['/prijava']);
      });
    } else {
      this.errorMessage = 'Please select file for each row';
    }
  }
}
