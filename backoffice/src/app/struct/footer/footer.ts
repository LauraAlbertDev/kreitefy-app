import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NAV_LINKS } from '../../shared/navigation';

@Component({
  selector: 'app-footer',
  imports: [RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {
  pages = NAV_LINKS;
}
