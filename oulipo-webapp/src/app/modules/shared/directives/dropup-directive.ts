import { Directive, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[appDropUpDirective]'
})
export class DropUpDirective {
  @HostBinding('class.open') 
  isOpen = false;
  isMouseCurrentlyIn = false;

  @HostListener('click') toggleOpen() {
      if (!this.isOpen) {
        this.isOpen = true;
      }
  }

  @HostListener('mouseleave') mouseLeave() {
    this.isMouseCurrentlyIn = false;
    setTimeout(() => {
      if (!this.isMouseCurrentlyIn) {
        this.isOpen = false;
      }
    }, 500);
  }

  @HostListener('mouseenter') mouseEnter() {
    this.isMouseCurrentlyIn = true;
  }
}
