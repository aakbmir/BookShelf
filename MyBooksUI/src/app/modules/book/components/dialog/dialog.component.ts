import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { MAT_DIALOG_DATA } from "@angular/material";
import { Book } from '../../book';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  comments: string;

  constructor(public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Book) {
    this.comments = data.comments;
  }

  ngOnInit() {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  updateComments() {
    this.dialogRef.close(this.comments);
  }
}