import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountPartsComponent } from './account-parts.component';

describe('AccountPartsComponent', () => {
  let component: AccountPartsComponent;
  let fixture: ComponentFixture<AccountPartsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountPartsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountPartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
