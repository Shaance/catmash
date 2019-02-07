import { CatWithVotes } from './../api/cat-with-votes';
import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

const allTime = 'All time\'s ';
const today = 'Today\'s ';
const votes = 'votes';
@Component({
  templateUrl: './stats.component.html'
})
export class StatsComponent implements OnInit {

  catsWithVotes: CatWithVotes;
  nextPeriodicity: string;

  constructor(private restService: RestService) {
    this.nextPeriodicity = today + votes;
  }

  ngOnInit() {
    this.getAllTimeMostVotedCat();
  }

  getAllTimeMostVotedCat() {
    this.nextPeriodicity = today + votes;
    this.restService.getAllTimeMostVotedCat()
    .subscribe(cats => this.catsWithVotes = cats);
  }

  getTodaysMostVotedCat() {
    this.nextPeriodicity = allTime + votes;
    this.restService.getTodayTimeMostVotedCat()
    .subscribe(cats => this.catsWithVotes = cats);
  }

  switchPeriodicity() {
    if (this.nextPeriodicity.includes(today)) {
        this.getTodaysMostVotedCat();
    } else {
        this.getAllTimeMostVotedCat();
    }
  }

}
