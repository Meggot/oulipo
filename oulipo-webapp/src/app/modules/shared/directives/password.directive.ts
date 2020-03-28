import { Directive, ElementRef} from '@angular/core';
@Directive({
  selector: '[appPassword]'
})
export class PasswordDirective {
 private _shown = false;
constructor(private el: ElementRef) {
    this.setup();
  }
  
toggle(span: HTMLElement) {
    this._shown = !this._shown;
    this.toggleEle(span);
  }

setup() {
    const parent = this.el.nativeElement.parentNode;
    const span = document.createElement('span');
    span.addEventListener('click', (event) => {
      this.toggle(span);
    });
    this.toggleEle(span);
    parent.appendChild(span);
  }
 
toggleEle(span: HTMLElement) {
    if (this._shown) {
        this.el.nativeElement.setAttribute('type', 'text');
        span.className = 'showPassword input-group-addon glyphicon glyphicon-eye-open';
      } else {
        this.el.nativeElement.setAttribute('type', 'password');
        span.className = 'showPassword input-group-addon glyphicon glyphicon-eye-close';
      }
}
}