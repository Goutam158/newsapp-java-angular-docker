
import { Component, OnInit, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';
import { FormControl ,FormsModule} from '@angular/forms';
import {CoreService} from '../core.service';
import { ArticleModel} from '../../models/ArticleModel';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs';
import { SourceModel } from '../../models/SourceModel';
import { MatSnackBar } from '@angular/material';
import { NgMatSearchBarModule } from 'ng-mat-search-bar';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  providers: [DatePipe]
})
export class SearchComponent {

  articles : Array<ArticleModel> = [];
  isAdded :string;
  searchText:string;
  constructor(private _coreService:CoreService,
             private _datePipe:DatePipe,
             private _snackBar : MatSnackBar) { }


  getNews(){
    console.log(this.searchText);
    if(this.searchText!=undefined || this.searchText!=null){
    this._coreService.getArticles(this.searchText,this.transformDate(new Date)).subscribe(res =>{
        res['articles'].forEach(element => {
          let articleModelNew = new ArticleModel(element.author,element.title,element.description,element.url,element.urlToImage,
          element.content,element.publishedAt,element.userId,element.isAdded,new SourceModel(element['source'].id,element['source'].name));
          articleModelNew.setIsAdded(this.getIsAddedInFav(element.publishedAt,element.title));
          this.articles.push(articleModelNew);
        
       });
  });
}
  }

  transformDate(myDate:Date) {
    return this._datePipe.transform(myDate, 'yyyy-MM-dd');
  }

  addToFavourite(article: ArticleModel): void{
    article.setIsAdded('true');
    this._coreService.addToFavMap(article['publishedAt'],article);

    this._coreService.addToFavourite(article).subscribe((response: any) => {
      this._snackBar.open(`Aricle added to wishlist successfully`,null,{duration : 2000,});
    });
    console.log(this._coreService.favMap.size + " **** after adding "+this._coreService.favMap.values());
  }
  
  getIsAddedInFav(publishedAt: string, title: string): string {
    let returnVal='false';
    if (this._coreService.favMap.size >0) {
      console.log(this._coreService.favMap.size + " **** after the time of search "+this._coreService.favMap.values());
      this._coreService.favMap.forEach((value: ArticleModel, key: string) => {
        let article: ArticleModel = this._coreService.favMap.get(key);
        if (article['publishedAt'] === publishedAt && article['title'] === title) {
          returnVal = 'true';
        }
      });
    }
     
    return returnVal;
    
    
  }

}


