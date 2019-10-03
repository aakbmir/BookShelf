import { Component, OnInit } from '@angular/core';
import { Book } from './../../book';
import { Author } from './../../author';
import { BookService } from '../../book.service';
import { ActivatedRoute } from "@angular/router";
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  books: Array<Book>;
  bookObj: Book;
  authorObj: Author;
  availability: string;
  title: string;
  subjectName: string;
  id: number;
  statusCode: number;
  errorStatus: string;
  authorName: string;
  searchBooks: Array<Book>;
  publisher: string;

  constructor(
    private bookService: BookService,
    private routes: ActivatedRoute,
    private matSnackbar: MatSnackBar,
    private router: Router

  ) {
    this.books = [];
  }

  ngOnInit() {
    const tempData = this.routes.data.subscribe(newData => {
      this.subjectName = newData.subject;
    });
    this.bookService.getBooks(this.subjectName).subscribe(books => {
      this.books = [];
      const data = books["works"];
      this.id = 0;
      for (let i = 0; i < data.length; i++) {
        this.bookObj = new Book();
        this.id++;
        this.title = data[i].title;
        this.authorObj = new Author();
        this.authorObj.name = data[i].authors[0].name;
        this.authorObj.authorId = this.id;
        this.bookObj.name = this.title;
        this.bookObj.author = this.authorObj;
        this.bookObj.bookId = this.subjectName.slice(0, 4) + this.id;
        this.books.push(this.bookObj);
        this.searchBooks = this.books;
      }
    });
  }

  onKey(event: any) {
    this.authorName = event.target.value;
    const result = this.searchBooks.filter(book => {
      let val = book.author.name.match(this.authorName) || book.name.match(this.authorName);
      return val;
    });

    this.books = result;
  }
  
  addToFavourites(book) {
    this.bookService.addBookToFavourites(book).subscribe(data => {
      this.statusCode = data.status;
      if (this.statusCode === 201) {

        this.matSnackbar.open("Book Successfully added in the Favourite's list  !!", " ", {
          duration: 1200,
          panelClass: ['success-snackbar']
        });
      }
    },
      error => {

        this.errorStatus = `${error.status}`;
        const errorMsg = `${error.error.message}`;
        this.statusCode = parseInt(this.errorStatus, 10);
        if (this.statusCode === 409) {
          this.matSnackbar.open(errorMsg, "", {
            duration: 1200,
            panelClass: ['error-snackbar']
          });
          this.statusCode = 0;
        }
      }

    );
  }
}