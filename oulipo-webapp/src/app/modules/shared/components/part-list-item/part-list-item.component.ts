import { Component, OnInit, Input } from '@angular/core';
import { PartModel } from '../../models/part.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-part-list-item',
  templateUrl: './part-list-item.component.html',
  styleUrls: ['./part-list-item.component.css']
})
export class PartListItemComponent implements OnInit {

  @Input('part')
  part: PartModel;
  
  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToProjectId() {
    this.router.navigate(['projects',this.part.projectId])
  }

}
