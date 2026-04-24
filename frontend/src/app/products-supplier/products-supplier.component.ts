import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-products-supplier',
  templateUrl: './products-supplier.component.html',
  styleUrls: ['./products-supplier.component.css'],
  standalone: false
})
export class ProductsSupplierComponent implements OnInit {
  idFournisseur: number = 0;
  produits: any[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.idFournisseur = Number(this.route.snapshot.paramMap.get('id'));
    this.fetchProduits();
  }

  fetchProduits(): void {
    fetch(`http://localhost:8084/products/getAllBySupplier/${this.idFournisseur}`)
      .then(res => res.json())
      .then((data) => {
        const produitsAvecQuantite = data.map(async (p: any) => {
          const quantity = await this.getQuantiteDisponible(p.id);
          return { ...p, quantity, quantiteCommande: 1 };
        });

        Promise.all(produitsAvecQuantite).then(result => {
          this.produits = result;
          console.log(this.produits);
        });
      })
      .catch(err => console.error(err));
  }

  getQuantiteDisponible(idProduct: number): Promise<{ id: number, quantity: number }> {
    return fetch(`http://localhost:8087/stocks/getStockBySupplierAndProduct/${this.idFournisseur}/${idProduct}`)
    .then(res => res.ok ? res.json() : null)
    .then(stock => ({
      id: typeof stock?.id === 'number' ? stock.id : 0,
      quantity: typeof stock?.quantity === 'number' ? stock.quantity : 0
    }))
    .catch(() => ({ id: 0, quantity: 0 }));
}


  commanderProduit(produit: any): void {
    const idEnterprise = Number(localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise'));

    if (!produit.quantiteCommande || produit.quantiteCommande <= 0) {
      alert("Veuillez entrer une quantité valide.");
      return;
    }

    if (produit.quantiteCommande > produit.quantity) {
      alert(`Stock insuffisant. Disponible : ${produit.quantity}`);
      return;
    }

    const order = {
      idEnterprise: idEnterprise,
      idSupplier: this.idFournisseur,
      state: "pending",
      products: [
        {
          idProduct: produit.id,
          quantity: produit.quantiteCommande
        }
      ]
    };

    fetch("http://localhost:8086/orders", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(order)
    })
    .then(res => {
      if (!res.ok) throw new Error("Erreur lors de la commande");
      return res.json();
    })
    .then(() => {
      alert("Commande envoyée !");
      produit.quantiteCommande = 1;
    })
    .catch(err => {
      console.error(err);
      alert("Une erreur est survenue.");
    });
  }
}
