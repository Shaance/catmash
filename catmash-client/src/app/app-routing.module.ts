import { CatsComponent } from './cats/cats.component';
import { StatsComponent } from './stats/stats.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: 'mash', component: CatsComponent},
  {path: '', redirectTo: '/mash', pathMatch: 'full'},
  {path: 'stats', component: StatsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
