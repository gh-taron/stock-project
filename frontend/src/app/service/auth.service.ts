import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private roleSubject = new BehaviorSubject<string | null>(localStorage.getItem('role') || sessionStorage.getItem('role'));
  role$ = this.roleSubject.asObservable();

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') || !!sessionStorage.getItem('token');
  }

  loginLocal(token: string, user: any): void {
    localStorage.setItem('token', token);
    localStorage.setItem('idUser', user.id);
    localStorage.setItem('role', user.role.toString());
    if (user.role !== "supplier") {
      localStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }
    this.roleSubject.next(user.role);
  }

  loginSession(token: string, user: any): void {
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('idUser', user.id);
    sessionStorage.setItem('role', user.role.toString());
    if (user.role !== "supplier") {
      sessionStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }
    this.roleSubject.next(user.role);
  }

  logoutLocal(): void {
    localStorage.clear();
    this.roleSubject.next(null);
  }

  logoutSession(): void {
    sessionStorage.clear();
    this.roleSubject.next(null);
  }
}
