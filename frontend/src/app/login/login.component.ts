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



  constructor(private router: Router, public authService : AuthService) {}

  async onSubmit() {
    if (this.email.trim() === '' || this.password.trim() === '') {
      console.error('L\'email et le mot de passe sont requis.');
      alert('L\'email et le mot de passe sont requis.');
      return;
    }

    try {
      const token = await this.loginUser(this.email, this.password);
      const user = await this.getUserByEmail(this.email);
    
      console.log('Connexion réussie, token:', token);
      if(this.rememberMe)
        this.authService.loginLocal(token, user);
      else
        this.authService.loginSession(token, user);
      this.router.navigate(["/"]);
    } catch (error) {
      console.error('Erreur de connexion:', error);
      alert('Erreur de connexion: ' + error);
    }
  }

  async getUserByEmail(email : string){
    try {
      const response = await fetch(`http://localhost:8082/users/findByEmail/${email}`,{
        method : 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      })

      if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }

      const user = await response.json();
      return user;
    } catch(error : any){
      console.error('Erreur lors de la récupération du user:', error.message);
      throw new Error(error.message);
    }
    
  }



  
  async loginUser(email: string, password: string) {
    try {
      const response = await fetch('http://localhost:8081/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }

      const data = await response.json();
      return data.token;
    } catch (error: any) {
      console.error('Erreur lors de la connexion:', error.message);
      throw new Error(error.message);
    }
  }
}
