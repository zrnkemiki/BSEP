import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CSRSubmitComponent } from './csrsubmit.component';

describe('CSRSubmitComponent', () => {
  let component: CSRSubmitComponent;
  let fixture: ComponentFixture<CSRSubmitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CSRSubmitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CSRSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
