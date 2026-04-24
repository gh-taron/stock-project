import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  role: string | null = null;

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.role$.subscribe(role => {
      this.role = role;
    });
  }

  logout(): void {
    this.authService.logoutLocal();
    this.authService.logoutSession();
    this.router.navigate(['/']);
  }
}
