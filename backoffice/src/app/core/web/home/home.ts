import { Component } from '@angular/core';
import { NAV_LINKS } from '../../../shared/navigation';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [
    RouterLink
  ],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  protected pages = NAV_LINKS;
}
