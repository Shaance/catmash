import { CatWithString } from './api/cat-with-string';
import { CatWithRatio } from './api/cat-with-ratio';
import { CatWithVotes } from './api/cat-with-votes';
import { CatMashRecord } from './api/cat-mash-record';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Cat } from './api/cat';

const endpoint = environment.host + ':8080/api/v1/cat';
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};
@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) { }

  getPairOfCats(): Observable<Cat> {
    return this.http.get(endpoint + '/pair')
    .pipe(catchError(this.handleError<any>('getPairOfCats')));
  }

  saveCatMashRecord(catMashRecord: CatMashRecord): Observable<CatMashRecord> {
    return this.http.post(endpoint + '/mash', JSON.stringify(catMashRecord), httpOptions).pipe(
      catchError(this.handleError<any>('saveCatMashRecord'))
    );
  }

  getCatsWithAllTimeVotesInDescOrder(): Observable<CatWithString[]> {
    return this.http.get<CatWithVotes[]>(endpoint + '/stats/votes/all').pipe(
        map(catArray => catArray.map(cat => new CatWithString(cat.cat, String(cat.votes)))
        ),  catchError(this.handleError<any>('getCatsWithAllTimeVotesInDescOrder'))
      );
  }

  getCatsWithTodayVotesInDescOrder(): Observable<CatWithString[]> {
    return this.http.get<CatWithVotes[]>(endpoint + '/stats/votes/today').pipe(
        map(catArray => catArray.map(cat => new CatWithString(cat.cat, String(cat.votes)))
        ), catchError(this.handleError<any>('getCatsWithTodayVotesInDescOrder'))
      );
  }

  getCatsWithAllTimeWinningRatioInDescOrder(): Observable<CatWithString[]> {
    return this.http.get<CatWithRatio[]>(endpoint + '/stats/ratio/all')
    .pipe(
        map(catArray => catArray.map(cat =>
          new CatWithString(cat.cat, cat.winningRatio === -1 ? 'N/A' : String(cat.winningRatio)))
        ), catchError(this.handleError<any>('getCatsWithAllTimeWinningRatioInDescOrder'))
      );
  }

  getCatsWithTodayWinningRatioInDescOrder(): Observable<CatWithString[]> {
    return this.http.get<CatWithRatio[]>(endpoint + '/stats/ratio/today')
    .pipe(
      map(catArray => catArray.map(cat =>
        new CatWithString(cat.cat, cat.winningRatio === -1 ? 'N/A' : String(cat.winningRatio)))
      ), catchError(this.handleError<any>('getCatsWithTodayWinningRatioInDescOrder'))
    );
  }


  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
