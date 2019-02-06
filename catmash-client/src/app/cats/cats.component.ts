import { Component, OnInit } from '@angular/core';
import { PairOfCats } from '../api/pair-of-cats';
import { RestService } from '../rest.service';

@Component({
  templateUrl: './cats.component.html'
})
export class CatsComponent implements OnInit {

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
