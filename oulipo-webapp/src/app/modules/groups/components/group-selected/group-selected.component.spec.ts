import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupSelectedComponent } from './group-selected.component';

describe('GroupSelectedComponent', () => {
  let component: GroupSelectedComponent;
  let fixture: ComponentFixture<GroupSelectedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupSelectedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupSelectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
