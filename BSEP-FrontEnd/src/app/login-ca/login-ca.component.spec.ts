import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginCAComponent } from './login-ca.component';

describe('LoginCAComponent', () => {
  let component: LoginCAComponent;
  let fixture: ComponentFixture<LoginCAComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginCAComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginCAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
