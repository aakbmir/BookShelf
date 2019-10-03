import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendComponent } from './recommend.component';

xdescribe('RecommendComponent', () => {
  let component: RecommendComponent;
  let fixture: ComponentFixture<RecommendComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecommendComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecommendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
