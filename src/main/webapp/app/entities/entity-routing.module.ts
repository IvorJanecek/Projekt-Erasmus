import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'natjecaj',
        data: { pageTitle: 'Natjecajs' },
        loadChildren: () => import('./natjecaj/natjecaj.module').then(m => m.NatjecajModule),
      },
      {
        path: 'mobilnost',
        data: { pageTitle: 'Mobilnosts' },
        loadChildren: () => import('./mobilnost/mobilnost.module').then(m => m.MobilnostModule),
      },
      {
        path: 'prijava',
        data: { pageTitle: 'Prijavas' },
        loadChildren: () => import('./prijava/prijava.module').then(m => m.PrijavaModule),
      },
      {
        path: 'fakultet',
        data: { pageTitle: 'Fakultets' },
        loadChildren: () => import('./fakultet/fakultet.module').then(m => m.FakultetModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
