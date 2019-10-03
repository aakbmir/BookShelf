import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CardContainerComponent } from './components/card-container/card-container.component';
import { CardComponent } from './components/card/card.component';

import { HeaderComponent } from './components/header/header.component';
import { AngularmaterialModule } from "../angularmaterial/angularmaterial.module";
import { AppRoutingModule } from '../../app-routing.module';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { FooterComponent } from './components/footer/footer.component';

import { DialogComponent } from './components/dialog/dialog.component';
import { BookService } from '../book/book.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorService } from './interceptor.service';
import { RecommendComponent } from './components/recommend/recommend.component';
import { ComponentsDirective } from './components.directive';

@NgModule({
  declarations: [
    CardContainerComponent,
    CardComponent,
    HeaderComponent,
    FavouritesComponent,
    FooterComponent,
    DialogComponent,
    RecommendComponent,
    ComponentsDirective,
    
    
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AngularmaterialModule,
  ],

  exports: [
    CardContainerComponent,
    HeaderComponent,
    AppRoutingModule,
    FooterComponent,
    FavouritesComponent,
    
  ],
  providers: [
    BookService,
    {
      provide:HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi:true
    }
  ],
  entryComponents: [
    DialogComponent
  ],
})
export class BooksModule { }
