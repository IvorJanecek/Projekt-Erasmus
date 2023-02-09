import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFakultet } from '../fakultet.model';

@Component({
  selector: 'jhi-fakultet-detail',
  templateUrl: './fakultet-detail.component.html',
})
export class FakultetDetailComponent implements OnInit {
  fakultet: IFakultet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fakultet }) => {
      this.fakultet = fakultet;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
