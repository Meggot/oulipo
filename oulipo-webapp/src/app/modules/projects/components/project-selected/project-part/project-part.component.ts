import {Component, Input} from '@angular/core';
import {PartModel} from '../../../../shared/models/part.model';

@Component({
  selector: 'app-project-part',
  templateUrl: './project-part.component.html',
  styleUrls: ['./project-part.component.css']
})
export class ProjectPartComponent {

  @Input('part')
  public part: PartModel;

  ngOnInit() {
  }


}
