import { Component, OnInit } from '@angular/core';
import { Book } from '../../book';
import { BookService } from '../../book.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {
books: Array<Book>;
favouritesFlag = true;
statusCode: number;

  constructor(
    private bookService: BookService,
    private snackbar: MatSnackBar) { }

  ngOnInit() {
    const message ="Favourites List is Empty !!";
    this.bookService.getAllBooksFromFavourites().subscribe(data => {
      this.books = data;
      if(data == null  || data.length ===0)
      {
        this.snackbar.open(message," ",{
          duration: 1500,
          panelClass: ['success-snackbar']
        });
      }
    });
  }

  deleteFromFavourite(book) {
    this.bookService.deleteBookFromFavourite(book).subscribe(data => {
      
      const index =this.books.indexOf(book);
      this.books.splice(index,1);
      this.snackbar.open("Book successfully deleted from favourite List !!" , " ",{
        duration: 1200,
        panelClass: ['success-snackbar']
    });
    });
    return this.books;
  }

  updateComments(book)
  {
    
    this.bookService.updateComments(book).subscribe(
    data => {
      this.snackbar.open("Comment successfully updated on the book !!","",{
        duration: 1200,
        panelClass: ['success-snackbar']
      });
    },
    error => {
    console.log("error", error);
  });
  }
}