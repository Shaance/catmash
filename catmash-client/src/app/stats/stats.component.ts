import { CatWithVotes } from './../api/cat-with-votes';
import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  templateUrl: './stats.component.html'
})
export class StatsComponent implements OnInit {

  catsWithVotes: CatWithVotes;

  constructor(private restService: RestService) {}

  ngOnInit() {
    this.getAllTimeMostVotedCat();
  }

  getAllTimeMostVotedCat() {
    this.restService.getAllTimeMostVotedCat()
    .subscribe(cats => this.catsWithVotes = cats);
  }

}
