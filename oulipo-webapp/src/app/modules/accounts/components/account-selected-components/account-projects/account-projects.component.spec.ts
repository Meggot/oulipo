import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountProjectsComponent } from './account-projects.component';

describe('AccountProjectsComponent', () => {
  let component: AccountProjectsComponent;
  let fixture: ComponentFixture<AccountProjectsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountProjectsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
