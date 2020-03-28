import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InboxBarComponent } from './inbox-bar.component';

describe('InboxBarComponent', () => {
  let component: InboxBarComponent;
  let fixture: ComponentFixture<InboxBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InboxBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InboxBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
