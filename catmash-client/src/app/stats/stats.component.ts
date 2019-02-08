import { CatWithString } from '../api/cat-with-string';
import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

const allTime = 'All time\'s ';
const today = 'Today\'s ';
const votes = 'votes';
const ratio = 'ratio';
@Component({
  templateUrl: './stats.component.html'
})
export class StatsComponent implements OnInit {

  catsWithString: CatWithString[];
  nextPeriodicity: string;
  mode: string;

  constructor(private restService: RestService) {
    this.nextPeriodicity = today + votes;
  }

  ngOnInit() {
    this.getCatsWithAllTimeVotesInDescOrder();
    this.mode = votes;
  }

  getCatsWithAllTimeVotesInDescOrder() {
    this.nextPeriodicity = today + votes;
    this.restService.getCatsWithAllTimeVotesInDescOrder()
    .subscribe(cats => this.catsWithString = cats);
  }

  getCatsWithAllTimeWinningRatioInDescOrder() {
    this.nextPeriodicity = today + ratio;
    this.restService.getCatsWithAllTimeWinningRatioInDescOrder()
    .subscribe(cats => this.catsWithString = cats);
  }

  getCatsWithTodayVotesInDescOrder() {
    this.nextPeriodicity = allTime + votes;
    this.restService.getCatsWithTodayVotesInDescOrder()
    .subscribe(cats => this.catsWithString = cats);
  }

  getCatsWithTodayWinningRatioInDescOrder() {
    this.nextPeriodicity = allTime + ratio;
    this.restService.getCatsWithTodayWinningRatioInDescOrder()
    .subscribe(cats => this.catsWithString = cats);
  }

  switchPeriodicity() {
    if (this.nextPeriodicity.includes(today)) {
      if (this.mode === votes) {
        this.getCatsWithTodayVotesInDescOrder();
      } else {
        this.getCatsWithTodayWinningRatioInDescOrder();
      }
    } else {
      if (this.mode === votes) {
        this.getCatsWithAllTimeVotesInDescOrder();
      } else {
        this.getCatsWithAllTimeWinningRatioInDescOrder();
      }
    }
  }

  // we want to keep the same periodicity when changing modes
  switchMode() {
    if (this.mode === votes) {
      this.mode = ratio;
      if (this.nextPeriodicity.includes(today)) {
        this.getCatsWithAllTimeWinningRatioInDescOrder();
      } else {
        this.getCatsWithTodayWinningRatioInDescOrder();
      }
    } else {
      this.mode = votes;
      if (this.nextPeriodicity.includes(today)) {
        this.getCatsWithAllTimeVotesInDescOrder();
      } else {
        this.getCatsWithTodayVotesInDescOrder();
      }
    }
  }

}
