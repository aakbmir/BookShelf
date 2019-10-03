import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from "./book";
import { USER_NAME } from "../authentication/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  thirdPartyBookApi: string;
  thirdPartyBookDetailsApi: string;
  bookApiKey: string;
  favouriteEndPoint: string;
  recommendedEndPoint: string;
  username: String;
  constructor(private httpClient: HttpClient) {
    this.thirdPartyBookApi = "http://openlibrary.org/subjects/";
    this.bookApiKey = ".json";
    this.thirdPartyBookDetailsApi = "http://openlibrary.org/search.json?title=";
    this.favouriteEndPoint = "http://localhost:8086/favouriteservice/api/v1/userbookservice/";
    this.recommendedEndPoint = "http://localhost:8086/recommendationservice/api/v1/userrecommendationbookservice";
  }


  getBooks(subject: string): Observable<any> {
    //const url = `${this.thirdPartyBookApi}${subject}${this.bookApiKey}`;
    //const url = `${this.thirdPartyBookDetailsApi}`;
    const url = `${this.thirdPartyBookApi}${subject}${this.bookApiKey}`;
    
    return this.httpClient.get(url);
  }

  getBooksDetails(title: string): Observable<any> {
    const url = `${this.thirdPartyBookDetailsApi}${title}`;
    
    return this.httpClient.get(url);
  }

  addBookToFavourites(book: Book) {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.favouriteEndPoint + "user/" + this.username + "/book";
    return this.httpClient.post(url, book, {
      observe: "response"
    });
  }

  addBookToRecommend(book: Book) {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.recommendedEndPoint + "/book";
    return this.httpClient.post(url, book, {
      observe: "response"
    });
  }

  getAllBooksFromFavourites(): Observable<Book[]> {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.favouriteEndPoint + "user/" + this.username + "/books";
    return this.httpClient.get<Book[]>(url);
  }


  getAllBooksFromRecommend(): Observable<Book[]> {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.recommendedEndPoint + "/books";
	  console.log('url login : ' + url);
    return this.httpClient.get<Book[]>(url);
  }


  deleteBookFromFavourite(book: Book) {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.favouriteEndPoint + "user/" + this.username + "/" + book.bookId;
    return this.httpClient.delete(url);
  }

  updateComments(book) {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.favouriteEndPoint + "user/" + this.username + "/book";

    return this.httpClient.patch(url, book, {
      observe: "response"
    });
  }
}
