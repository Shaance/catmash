import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { StatsComponent } from './stats/stats.component';
import { RestService } from './rest.service';
import { CatsComponent } from './cats/cats.component';

@NgModule({
  declarations: [
    AppComponent,
    StatsComponent,
    CatsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
