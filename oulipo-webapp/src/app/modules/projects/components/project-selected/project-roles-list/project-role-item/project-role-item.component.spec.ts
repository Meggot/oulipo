import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectRoleItemComponent } from './project-role-item.component';

describe('ProjectRoleItemComponent', () => {
  let component: ProjectRoleItemComponent;
  let fixture: ComponentFixture<ProjectRoleItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectRoleItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectRoleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
