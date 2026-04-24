import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-supplier',
  standalone: false,
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.css']
})
export class SupplierComponent implements OnInit {
  suppliers: any[] = [];
  filteredSuppliers: any[] = [];
  searchTerm: string = '';
  favoriteSupplierIds: number[] = [];
  idEnterprise: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.idEnterprise = this.getEnterpriseId();
    if (!this.idEnterprise) return;

    fetch(`http://localhost:8082/users/getAllSuppliers`)
      .then(res => res.json())
      .then(data => {
        this.suppliers = data;
        this.filteredSuppliers = data;
      })
      .catch(err => console.error('Erreur de récupération des fournisseurs', err));

    fetch(`http://localhost:8082/users/favoriteSuppliers/${this.idEnterprise}`)
      .then(res => res.json())
      .then(data => {
        this.favoriteSupplierIds = data.map((supplier: any) => supplier.id);
      })
      .catch(err => console.error('Erreur de récupération des favoris', err));
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

  isFavorite(supplier: any): boolean {
    return this.favoriteSupplierIds.includes(supplier.id);
  }

  toggleFavorite(supplier: any) {
    const idSupplier = supplier.id;
    const isAlreadyFavorite = this.favoriteSupplierIds.includes(idSupplier);

    const url = isAlreadyFavorite
      ? `http://localhost:8082/users/favoriteSuppliers/deleteOne`
      : `http://localhost:8082/users/favoriteSuppliers/addOne`;

    const method = isAlreadyFavorite ? 'DELETE' : 'POST';

    fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ idEnterprise: this.idEnterprise, idSupplier })
    })
      .then(() => {
        if (isAlreadyFavorite) {
          this.favoriteSupplierIds = this.favoriteSupplierIds.filter(id => id !== idSupplier);
        } else {
          this.favoriteSupplierIds.push(idSupplier);
        }
      })
      .catch(err => console.error("Erreur lors du changement de favori", err));
  }

  goFavsPage() {
    this.router.navigate(["/favorite-suppliers"]);
  }

  voirProduits(idFournisseur: number) {
    this.router.navigate(['/produits-fournisseur', idFournisseur]);
  }
}
