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
  selectedFiles: File[][] = []; // Explicitly define the type as File[][]
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
      this.selectedFiles = this.zahtjevs ? this.zahtjevs.map(() => []) : []; // Initialize the selectedFiles array with an empty array for each row
      this.checkAllFilesSelected(); // Check if all files are selected and enable/disable the form field
    });
  }

  onFileSelect(event: Event, column: number): void {
    const target = event.target as HTMLInputElement;
    if (target.files !== null) {
      const file = target.files[0];
      const fileSizeInBytes = file.size;
      const maxSizeInBytes = 1048576; // 1 MB in bytes

      if (fileSizeInBytes > maxSizeInBytes) {
        this.errorMessage = 'Dokument je veći od 1MB!';
        this.fileNames[column] = ''; // Set an empty string for the file name
        target.value = '';
        return;
      }

      if (!Array.isArray(this.selectedFiles[column])) {
        this.selectedFiles[column] = [];
      }
      this.selectedFiles[column].push(file);

      if (!Array.isArray(this.formData[column])) {
        this.formData[column] = [];
      }
      const formData = new FormData();
      formData.append('files', file, file.name);
      this.formData[column].push(formData);

      this.fileNames[column] = file.name;
    } else {
      this.errorMessage = 'Nije odabran niti jedan dokument';
    }

    const allFilesSelected = this.selectedFiles.every(files => files.length > 0);
    if (allFilesSelected) {
      this.fileForm.get('files')!.enable();
    } else {
      this.fileForm.get('files')!.disable();
    }
    this.checkAllFilesSelected();
  }

  checkAllFilesSelected(): void {
    const allFilesSelected = this.selectedFiles.length === this.zahtjevs?.length && this.selectedFiles.every(files => files.length > 0);
    if (allFilesSelected) {
      this.fileForm.get('files')!.enable();
    } else {
      this.fileForm.get('files')!.disable();
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
      // Display the error message if any row is missing a selected file
      this.errorMessage = 'Odaberite dokument za svaki red';
    }
  }
}
