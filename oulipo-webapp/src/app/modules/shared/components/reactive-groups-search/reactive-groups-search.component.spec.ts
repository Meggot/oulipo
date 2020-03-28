import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReactiveGroupsSearchComponent } from './reactive-groups-search.component';

describe('ReactiveGroupsSearchComponent', () => {
  let component: ReactiveGroupsSearchComponent;
  let fixture: ComponentFixture<ReactiveGroupsSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReactiveGroupsSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReactiveGroupsSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
