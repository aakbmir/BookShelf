import { NgModule, OnInit, Input } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardContainerComponent } from 'src/app/modules/book/components/card-container/card-container.component';
import { FavouritesComponent } from './modules/book/components/favourites/favourites.component';
import { RecommendComponent } from './modules/book/components/recommend/recommend.component';
import { RegisterComponent } from './modules/authentication/components/register/register.component';
import { LoginComponent } from './modules/authentication/components/login/login.component';
import { AuthGuardService } from 'src/app/modules/book/auth-guard.service';

const routes: Routes = [

  {
    path: "",
    component: LoginComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "Fiction",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "fiction" }
  },
  {
    path: "Science",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "science" }
  },
  {
    path: "Biographies",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "biographies" }
  },
  {
    path: "Textbooks",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "textbooks" }
  },
  {
    path: "Sci-Fi",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "sci-fi" }
  },
  {
    path: "Romance",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "romance" }
  },
  {
    path: "Fantasy",
    component: CardContainerComponent,
    canActivate: [AuthGuardService],
    data: { subject: "fantasy" }
  },
  {
    path: "Favourites",
    component: FavouritesComponent,
    canActivate: [AuthGuardService]

  },
  {
    path: "Recommend",
    component: RecommendComponent,
    canActivate: [AuthGuardService]
  },
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }