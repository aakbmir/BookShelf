import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Book } from '../../book';
import { BookService } from '../../book.service';
import { MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-recommend',
  templateUrl: './recommend.component.html',
  styleUrls: ['./recommend.component.css']
})
export class RecommendComponent implements OnInit 
{
  books: Array<Book>;
  favouritesFlag = true;
  statusCode: number;
  errorStatus: string;

  constructor(private bookService: BookService,private snackbar: MatSnackBar)
  {
  }

  ngOnInit()
  {
    const message = "Recommended List is Empty";
    this.bookService.getAllBooksFromRecommend().subscribe(data => {
      this.books = data;
      if (data.length === 0) {
        this.snackbar.open(message, " ", {
          duration: 1200,
          panelClass: ['success-snackbar']
        });
      }
    });
  }

  addToFavourites(book){
    this.bookService.addBookToFavourites(book).subscribe(data => {
      this.statusCode = data.status;
      if(this.statusCode===201) {
        
        this.snackbar.open("Book Successfully added in the Favourite's List !!"," ",{
          duration: 1200,
          panelClass: ['success-snackbar']
        });
      }
    },
    error => {
      
      this.errorStatus=`${error.status}`;
      const errorMsg =`${error.error.message}`;
      this.statusCode = parseInt(this.errorStatus,10);
      if(this.statusCode ===409)
      {
        this.snackbar.open(errorMsg,"",{
          duration:1500,
          panelClass: ['error-snackbar']
        });
        this.statusCode=0;
      }
    }
  
  );
  }

}