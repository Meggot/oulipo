import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { ProjectTagModel } from '../../models/project-tag-model.model';

@Component({
  selector: 'app-tags-input',
  templateUrl: './tags-input.component.html',
  styleUrls: ['./tags-input.component.css']
})
export class TagsInputComponent implements OnInit {

  @Input()
  tags: ProjectTagModel[];
  tagDisplay: string[];

  @Output()
  newTagValue: EventEmitter<string>
  @Output()
  removeTagValue: EventEmitter<number>
  
  constructor() {     
  this.newTagValue = new EventEmitter<string>();
  this.removeTagValue = new EventEmitter<number>();
  this.tags = [];
  this.tagDisplay = [];
}

  ngOnInit() {
    this.tags.map(
      (tag: ProjectTagModel) => {
        this.tagDisplay.push(tag.value);
      }
    )
  }

  addNewTag(tagValue: string) {
    this.tagDisplay.push(tagValue);
    this.newTagValue.emit(tagValue);
  }

  removeTag(index: number){
    this.tags.splice(index, 1);
  }

}
