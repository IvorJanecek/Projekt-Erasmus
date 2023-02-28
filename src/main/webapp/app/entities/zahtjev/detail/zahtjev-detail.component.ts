import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IZahtjev } from '../zatjev.model';

@Component({
  selector: 'jhi-zahtjev-detail',
  templateUrl: './zahtjev-detail.component.html',
})
export class ZahtjevDetailComponent implements OnInit {
  zahtjev: IZahtjev | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zahtjev }) => {
      this.zahtjev = zahtjev;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
