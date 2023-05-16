import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IMobilnost } from '../mobilnost.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { HttpClient } from '@angular/common/http';
import { UploadFileService } from './upload/upload-files-service.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IUploadFile } from 'app/entities/prijava/upload_files.model';
import { FileModalComponent } from './file-modal/file-modal.component';

@Component({
  selector: 'jhi-mobilnost-detail',
  templateUrl: './mobilnost-detail.component.html',
})
export class MobilnostDetailComponent implements OnInit {
  mobilnost: IMobilnost | null = null;
  uploadFiles?: IUploadFile[];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private http: HttpClient,
    private uploadFileService: UploadFileService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mobilnost }) => {
      this.mobilnost = mobilnost;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }
  uploadajFajl(mobilnost: Pick<IMobilnost, 'id'>) {
    const mobilnostId = mobilnost;
    if (mobilnostId) {
      const route = `/mobilnost/${mobilnostId.id}/upload`;
      this.router.navigate([route], { state: { mobilnost: mobilnostId.id } });
    }
  }

  urediFile(file: Pick<IUploadFile, 'id'>): void {
    const modalRef = this.modalService.open(FileModalComponent, { centered: true });

    modalRef.componentInstance.uploadFile = file;

    modalRef.result.then(
      yes => {
        console.log('Ok click');
      },
      cancel => {
        console.log('cancel Click');
        window.location.reload();
      }
    );
  }

  deleteFile(fileId: number): void {
    this.uploadFileService.deleteFile(fileId).subscribe(() => {
      this.mobilnost!.uploadFiles = this.mobilnost?.uploadFiles!.filter(uploadFile => uploadFile.id !== fileId);
    });
    window.location.reload();
  }

  onModalHidden(): void {
    window.location.reload();
  }

  previousState(): void {
    window.history.back();
  }
}
