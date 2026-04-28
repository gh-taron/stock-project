import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-supplier-favorite',
  templateUrl: './supplier-favorite.component.html',
  styleUrl: './supplier-favorite.component.css',
  standalone: false
})
export class SupplierFavoriteComponent {
  suppliers: any[] = [];
  filteredSuppliers: any[] = [];
  searchTerm: string = '';
  idEnterprise: number = 0;

  constructor(private router: Router) {}

  async ngOnInit(): Promise<void> {
    this.idEnterprise = this.getEnterpriseId();
    if (!this.idEnterprise) return;

    try {
      const idsRes = await fetch(`http://localhost:8082/users/favoriteSupplierIds/${this.idEnterprise}`);
      const ids: number[] = await idsRes.json();

      const suppliers = await Promise.all(
        ids.map(id => fetch(`http://localhost:8083/enterprises/${id}`).then(r => r.json()))
      );

      this.suppliers = suppliers;
      this.filteredSuppliers = suppliers;
    } catch (err) {
      console.error('Erreur de récupération des favoris', err);
    }
  }

  getEnterpriseId(): number {
    const id = localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise');
    return id ? parseInt(id, 10) : 0;
  }

  filterSuppliers() {
    const term = this.searchTerm.toLowerCase();
    this.filteredSuppliers = this.suppliers.filter(supplier =>
      supplier.name.toLowerCase().includes(term) ||
      supplier.email.toLowerCase().includes(term) ||
      supplier.phoneNumber.includes(term)
    );
  }

  removeFromFavorites(supplierId: number) {
    const body = {
      idEnterprise: this.idEnterprise,
      idSupplier: supplierId
    };

    fetch('http://localhost:8082/users/favoriteSuppliers/deleteOne', {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    }).then(res => {
      if (!res.ok) throw new Error('Erreur lors de la suppression');
      this.suppliers = this.suppliers.filter(s => s.id !== supplierId);
      this.filteredSuppliers = this.filteredSuppliers.filter(s => s.id !== supplierId);
    }).catch(err => alert(err.message));
  }


  voirProduits(idFournisseur: number) {
    this.router.navigate(['/products-supplier', idFournisseur]);
  }
}
