import { Directive, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
export class DropdownDirective {
  @HostBinding('class.open') 
  isOpen = false;
  isMouseCurrentlyIn = false;

  @HostListener('click') toggleOpen() {
    this.isOpen = !this.isOpen;
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
