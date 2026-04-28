import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  standalone: false
})
export class RegisterComponent {
  enterprise = {
    name: '',
    email: '',
    phoneNumber: '',
    address: '',
    type: ''
  };

  user = {
    firstname: '',
    lastname: '',
    email: '',
    phoneNumber: '',
    password: '',
    role: 'admin'
  };

  confirmPassword: string = '';

  constructor(private router: Router) {}

  async onSubmit() {
    if (this.user.password !== this.confirmPassword) {
      alert("Les mots de passe ne correspondent pas.");
      return;
    }

    if (!this.enterprise.type) {
      alert("Choisis le type d'entreprise (acheteur ou fournisseur).");
      return;
    }

    try {
      const resEnterprise = await fetch('http://localhost:8083/enterprises', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.enterprise)
      });

      if (!resEnterprise.ok) throw new Error(await resEnterprise.text());

      const createdEnterprise = await resEnterprise.json();
      const userWithEnterprise = {
        ...this.user,
        idEnterprise: createdEnterprise.id
      };

      const resUser = await fetch('http://localhost:8081/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userWithEnterprise)
      });

      if (!resUser.ok) throw new Error(await resUser.text());

      this.router.navigate(['/login']);

    } catch (error: any) {
      alert('Erreur : ' + error.message);
    }
  }
}
