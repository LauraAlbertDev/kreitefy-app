import { Component } from '@angular/core';
import { NAV_LINKS } from '../../shared/navigation';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {
  pages = NAV_LINKS;
}
