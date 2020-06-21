import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrRequestViewerComponent } from './csr-request-viewer.component';

describe('CsrRequestViewerComponent', () => {
  let component: CsrRequestViewerComponent;
  let fixture: ComponentFixture<CsrRequestViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsrRequestViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrRequestViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
