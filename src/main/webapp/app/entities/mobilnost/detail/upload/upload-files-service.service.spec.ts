import { TestBed } from '@angular/core/testing';

import { UploadFilesServiceService } from './upload-files-service.service';

describe('UploadFilesServiceService', () => {
  let service: UploadFilesServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UploadFilesServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
