import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectDashboardEditsComponent } from './project-dashboard-edits.component';

describe('ProjectDashboardEditsComponent', () => {
  let component: ProjectDashboardEditsComponent;
  let fixture: ComponentFixture<ProjectDashboardEditsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectDashboardEditsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectDashboardEditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
