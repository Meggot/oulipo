import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountGroupsComponent } from './account-groups.component';

describe('AccountGroupsComponent', () => {
  let component: AccountGroupsComponent;
  let fixture: ComponentFixture<AccountGroupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountGroupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
