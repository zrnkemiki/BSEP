import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrViewerComponent } from './csr-viewer.component';

describe('CsrViewerComponent', () => {
  let component: CsrViewerComponent;
  let fixture: ComponentFixture<CsrViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsrViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
