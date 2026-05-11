import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-products-supplier',
  templateUrl: './products-supplier.component.html',
  styleUrls: ['./products-supplier.component.css'],
  standalone: false
})
export class ProductsSupplierComponent implements OnInit {
  idSupplier: number = 0;
  products: any[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {  
    this.idSupplier = Number(this.route.snapshot.paramMap.get('id'));
    this.fetchProducts();
  }

  fetchProducts(): void {
  fetch(`http://localhost:8084/products/getAllByEnterprise/${this.idSupplier}`)
    .then(res => res.json())
    .then(async (data) => {
      const productsWithQuantity = await Promise.all(
        data.map(async (p: any) => {
          const stock = await this.getQuantiteDisponible(p.id);
          return { ...p, quantity: stock.quantity, orderQuantity: 1 };
        })
      );
      this.products = productsWithQuantity;
      console.log(this.products);
    })
    .catch(err => console.error(err));
}

  getQuantiteDisponible(idProduct: number): Promise<{ quantity: number }> {
    return fetch(`http://localhost:8087/stocks/getStockBySupplierEnterpriseAndProduct/${this.idSupplier}/${idProduct}`)
      .then(res => res.ok ? res.json() : null)
      .then(stock => ({
        quantity: typeof stock?.quantity === 'number' ? stock.quantity : 0
      }))
      .catch(() => ({ quantity: 0 }));
  }


  orderProduct(product: any): void {
    const idBuyer = Number(localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise'));

    if (!product.orderQuantity || product.orderQuantity <= 0) {
      alert("Veuillez entrer une quantité valide.");
      return;
    }

    if (product.orderQuantity > product.quantity) {
      alert(`Stock insuffisant. Disponible : ${product.quantity}`);
      return;
    }

    const order = {
      idBuyer: idBuyer,
      idSupplier: this.idSupplier,
      state: "pending",
      products: [
        { idProduct: product.id, quantity: product.orderQuantity }
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
        product.orderQuantity = 1;
      })
      .catch(err => {
        console.error(err);
        alert("Une erreur est survenue.");
      });
  }
}
