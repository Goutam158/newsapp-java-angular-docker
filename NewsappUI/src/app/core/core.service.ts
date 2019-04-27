import { Injectable } from '@angular/core';
import { HttpClient ,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticleModel } from '../models/ArticleModel';


@Injectable({
  providedIn: 'root'

})
export class CoreService {

  private newsApi: string = "https://newsapi.org/v2/everything?";
  private newsApiKey: string = "23fc747ac3e947c5a976c5b1dc628281";
  private favouriteApi: string = "http://localhost:9040/news/api/v1";
   favMap : Map<string,ArticleModel> = new Map<string,ArticleModel>() ;
  
  constructor(private _http: HttpClient) { }

  getArticles(query: string, from: string): Observable<any> {
    let finalURL: string = this.newsApi + "q=" + query + "&from=" + from + "&sortBy=publishedAt&" + "apiKey=" + this.newsApiKey;
    return this._http.get(finalURL);

  }
  addToFavourite(article: ArticleModel): Observable<any> {
   
    return this._http.post(this.favouriteApi + '/article', article, { responseType: 'text' });
  }

  fetchMyArticles(): Observable<any> {
    return this._http.get(this.favouriteApi + '/articles');
  }


  deleteArticle(article: ArticleModel): Observable<any> {
    const params = new HttpParams()
                     .set('author',article.author)
                     .set('publishedAt',article.publishedAt);
    return this._http.delete(this.favouriteApi + '/article',{params});
  }

  addToFavMap(key:string,article:ArticleModel):void{
     this.favMap.set(key,article);
  }
  deleteFromFavMap(key:string):void{
    this.favMap.delete(key);
  }

}


