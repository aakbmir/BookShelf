import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Book } from '../../book';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogComponent } from '../../components/dialog/dialog.component';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
 
  @Input()
  book: Book;

  @Input()
  favouritesFlag: boolean;

  @Output()
  addToFavourites = new EventEmitter();


  @Output()
  deleteFromFavourites = new EventEmitter();

  @Output()
  updateComments = new EventEmitter();

  constructor(private dialog: MatDialog) { }

  addButtonClick(book) {

    this.addToFavourites.emit(book);
  }

  ngOnInit() {
  }

  deletebutton(book) {
    this.deleteFromFavourites.emit(book);
  }

  addComments() {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: { comments: this.book.comments }
    });
    dialogRef.afterClosed().subscribe(result => {

      this.book.comments = result;
      this.updateComments.emit(this.book);
    })
  }


}
