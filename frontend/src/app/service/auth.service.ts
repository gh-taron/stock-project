import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private roleSubject = new BehaviorSubject<string | null>(localStorage.getItem('role') || sessionStorage.getItem('role'));
  role$ = this.roleSubject.asObservable();

  private enterpriseTypeSubject = new BehaviorSubject<string | null>(localStorage.getItem('enterpriseType') || sessionStorage.getItem('enterpriseType'));
  enterpriseType$ = this.enterpriseTypeSubject.asObservable();

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') || !!sessionStorage.getItem('token');
  }

  isSupplier(): boolean {
    const t = localStorage.getItem('enterpriseType') || sessionStorage.getItem('enterpriseType');
    return t === 'supplier';
  }

  isBuyer(): boolean {
    const t = localStorage.getItem('enterpriseType') || sessionStorage.getItem('enterpriseType');
    return t === 'buyer';
  }

  loginLocal(token: string, user: any, enterpriseType: string): void {
    this.persist(localStorage, token, user, enterpriseType);
  }

  loginSession(token: string, user: any, enterpriseType: string): void {
    this.persist(sessionStorage, token, user, enterpriseType);
  }

  private persist(storage: Storage, token: string, user: any, enterpriseType: string) {
    storage.setItem('token', token);
    storage.setItem('idUser', user.id);
    storage.setItem('role', user.role.toString());
    storage.setItem('idEnterprise', user.idEnterprise.toString());
    storage.setItem('enterpriseType', enterpriseType);
    this.roleSubject.next(user.role);
    this.enterpriseTypeSubject.next(enterpriseType);
  }

  logoutLocal(): void {
    localStorage.clear();
    this.roleSubject.next(null);
    this.enterpriseTypeSubject.next(null);
  }

  logoutSession(): void {
    sessionStorage.clear();
    this.roleSubject.next(null);
    this.enterpriseTypeSubject.next(null);
  }
}
