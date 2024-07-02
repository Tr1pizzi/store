import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./routes/home/home.component";
import {negozioArticoliComponent} from "./routes/negozioArticoli/negozio";
import {azienda} from "./routes/azienda/azienda.component";
import {AccountComponent} from "./routes/account/profilo.component";
import {authGuard} from "./aiuto/auth.guard";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard]
  },
  {
    path: 'negozioArticoli',
    component: negozioArticoliComponent,
    canActivate: [authGuard]
  },
  {
    path: 'azienda',
    component: azienda,
    canActivate: [authGuard]
  },
  {
    path: 'account',
    component: AccountComponent,
    canActivate: [authGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
