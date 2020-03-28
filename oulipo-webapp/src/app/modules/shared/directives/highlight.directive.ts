import {Directive, HostBinding, HostListener} from '@angular/core';

@Directive({
  selector: '[appHighlightDirective]'
})
export class HighlightDirective {

  @HostBinding('class.active')
  mouseInside = false;

  @HostListener('mouseleave')
  mouseLeave() {
    this.mouseInside = false;
  }

  @HostListener('mouseenter')
  mouseEnter() {
    this.mouseInside = true;
  }
}
