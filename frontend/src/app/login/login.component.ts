import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';


@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  rememberMe: boolean = false;
  showPassword = false;

  constructor(private router: Router, public authService: AuthService) {}

  async onSubmit() {
    if (this.email.trim() === '' || this.password.trim() === '') {
      alert('L\'email et le mot de passe sont requis.');
      return;
    }

    try {
      const token = await this.loginUser(this.email, this.password);
      const user = await this.getUserByEmail(this.email);
      const enterprise = await this.getEnterpriseById(user.idEnterprise);

      if (this.rememberMe)
        this.authService.loginLocal(token, user, enterprise.type);
      else
        this.authService.loginSession(token, user, enterprise.type);

      this.router.navigate(["/"]);
    } catch (error) {
      console.error('Erreur de connexion:', error);
      alert('Erreur de connexion: ' + error);
    }
  }

  async getUserByEmail(email: string) {
    const response = await fetch(`http://localhost:8082/users/findByEmail/${email}`);
    if (!response.ok) throw new Error(await response.text());
    return response.json();
  }

  async getEnterpriseById(id: number) {
    const response = await fetch(`http://localhost:8083/enterprises/${id}`);
    if (!response.ok) throw new Error(await response.text());
    return response.json();
  }

  async loginUser(email: string, password: string) {
    const response = await fetch('http://localhost:8081/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    if (!response.ok) throw new Error(await response.text());

    const data = await response.json();
    return data.token;
  }
}
