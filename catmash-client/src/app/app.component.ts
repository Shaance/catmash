import { PairOfCats } from './pair-of-cats';
import { Component, OnInit } from '@angular/core';
import { RestService } from './rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Cat Mash';
  pairOfCats: PairOfCats;

  constructor(private restService: RestService) {}

  ngOnInit() {
    this.getPair();
  }

  saveMashRecord(winnerCatId: String, loserCatId: String) {
    this.restService.saveCatMashRecord({
      winnerCatId, loserCatId
    }).subscribe(_ => this.getPair());
  }

  getPair() {
    this.restService.getPairOfCats()
    .subscribe(pair => this.pairOfCats = pair);
  }
}
