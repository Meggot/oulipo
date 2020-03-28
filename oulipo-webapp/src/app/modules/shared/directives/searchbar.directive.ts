import {Directive, HostBinding, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appSearchBarDirective]'
})
export class SearchbarDirective {

  @Input('text')
  searchText: string;

  @HostBinding('class.open')
  isOpen = false;

  mouseInside: false;

  @HostListener('keyup') toggleOpen() {
    if (this.searchText.length > 0) {
      this.isOpen = true;
    } else if (this.searchText.length === 0) {
      this.isOpen = false;
    }
  }

  @HostListener('focusout') focusOut() {
    setTimeout(() => {
      if (!this.mouseInside) {
        this.isOpen = false;
      }
    }, 500);
  }

  @HostListener('focusin') focusIn() {
    this.isOpen = true;
  }

  @HostListener('mousein') mouseIn() {
    this.mouseInside = false;
  }

  @HostListener('mouseout') mouseOut() {
    this.mouseInside = false;
  } 
}
