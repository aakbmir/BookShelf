import { TestBed } from '@angular/core/testing';
import { BookService } from './book.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Book } from './book';

describe('BookService', () => {
  
let book = new Book();
book = {
    bookId:"book123",
    name:"bookname",
    comments:"new comments",
    author:{
      authorId:123,
      name:"authorname"
  }
};


  const favouriteEndPoint = "http://localhost:8086/favouriteservice/api/v1/userbookservice/";
  let bookService:BookService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BookService]
    });
        bookService = TestBed.get(BookService);
        httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(bookService).toBeTruthy();
  });

  it("#addbookToFavourite should fetch proper response from Http Call",()=> {
    bookService.addBookToFavourites(book).subscribe(res=>{
      expect(res.body).toBe(book);
    });

    const url = favouriteEndPoint + "user/" + "Test" + "/book";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(url);
  });

  it("#getAllBooksFromFavouritesList should fetch proper response from Http Call",()=> {
    bookService.getAllBooksFromFavourites().subscribe(res=>{});

    const url = favouriteEndPoint + "user/" + "Test" + "/books";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('GET');
    expect(httpMockReq.request.url).toEqual(url);
  });

  it("#deleteBooksFromFavouritesList should fetch proper response from Http Call",()=> {
    bookService.deleteBookFromFavourite(book).subscribe(res=>{
      expect(res.body).toBe(book);
    });

    const url = favouriteEndPoint + "user/" + "Test/" + book.bookId;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('DELETE');
    expect(httpMockReq.request.url).toEqual(url);
  });


  it("#updateComments should fetch proper response from Http Call",()=> {
    bookService.updateComments(book).subscribe(res=>{
      expect(res.body).toBe(book);
    });

    const url = favouriteEndPoint + "user/" + "Test/" + "book";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('PATCH');
    expect(httpMockReq.request.url).toEqual(url);
  });
});
