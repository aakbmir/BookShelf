import { BrowserModule } from '@angular/platform-browser';
import { NgModule,OnInit, Input, } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BooksModule } from './modules/book/book.module';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationModule} from './modules/authentication/authentication.module';
import { CardComponent } from 'src/app/modules/book/components/card/card.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BooksModule,
    AuthenticationModule,
    ReactiveFormsModule
  ],
  providers: [CardComponent],
  bootstrap: [AppComponent]
})
export class AppModule {

 }
