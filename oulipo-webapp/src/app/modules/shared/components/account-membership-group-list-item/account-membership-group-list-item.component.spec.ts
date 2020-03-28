import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountMembershipGroupListItemComponent } from './account-membership-group-list-item.component';

describe('AccountMembershipGroupListItemComponent', () => {
  let component: AccountMembershipGroupListItemComponent;
  let fixture: ComponentFixture<AccountMembershipGroupListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountMembershipGroupListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountMembershipGroupListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
