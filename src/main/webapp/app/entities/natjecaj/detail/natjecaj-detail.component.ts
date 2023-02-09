import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatjecaj } from '../natjecaj.model';

@Component({
  selector: 'jhi-natjecaj-detail',
  templateUrl: './natjecaj-detail.component.html',
})
export class NatjecajDetailComponent implements OnInit {
  natjecaj: INatjecaj | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natjecaj }) => {
      this.natjecaj = natjecaj;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
