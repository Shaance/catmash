import { Component, OnInit } from '@angular/core';
import { RestService } from './rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'catmash-client';
  pairOfCats;

  constructor(private restService : RestService) {}

  ngOnInit() {
    this.getPair();
  }

  saveMashRecord(winner, loser) {
    this.restService.saveCatMashRecord({
      winnerCat: winner,
      loserCat: loser
    }).subscribe(_ => this.getPair())
  }

  getPair(){
    this.restService.getPairOfCats()
    .subscribe(pair => this.pairOfCats = pair);
  }
}
