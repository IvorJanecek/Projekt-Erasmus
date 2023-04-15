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
  selectedFiles!: FileList;
  formData: FormData = new FormData();
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
  }

  uploadFiles(): void {
    const prijavaId = this.route.snapshot.params.prijavaId; // Replace with the ID of the Prijava instance
    this.http.post(`/api/uploadFiles/${prijavaId}`, this.formData).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
    this.router.navigate(['/prijava']);
  }
}
