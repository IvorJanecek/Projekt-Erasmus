import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IPrijava } from 'app/entities/prijava/prijava.model';

import { INatjecaj } from '../natjecaj.model';

@Component({
  selector: 'jhi-natjecaj-detail',
  templateUrl: './natjecaj-detail.component.html',
})
export class NatjecajDetailComponent implements OnInit {
  natjecaj: INatjecaj | null = null;
  prijavaService: any;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natjecaj }) => {
      this.natjecaj = natjecaj;
    });
  }

  createNewPrijava(natjecaj: number): void {
    const newPrijava: IPrijava = {
      natjecaj,
      id: 0,
    };
    this.router.navigate(['/prijava/new'], { state: { prijava: newPrijava } });
  }

  previousState(): void {
    window.history.back();
  }
}
