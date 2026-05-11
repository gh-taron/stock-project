import { Component } from '@angular/core';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.css',
  standalone: false
})
export class StockComponent {
  products: any[] = [];
  filteredStocks: any[] = [];
  searchTerm: string = '';
  isAdding: boolean = false;
  quantity: number = 0;
  stocks: any[] = [];

  newProduct: any = {
    name: '',
    type: '',
    description: ''
  };

  editingProductId: number | null = null;
  editProductData: any = { name: '', type: '', description: '', quantity: 0 };

  idEnterprise: number = 0;
  enterpriseType: string | null = localStorage.getItem('enterpriseType') || sessionStorage.getItem('enterpriseType') ;

  ngOnInit(): void {
    this.idEnterprise = this.getEnterpriseId();
    this.fetchProductsAndStocks();
  }

  getEnterpriseId(): number {
    const id = localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise');
    return id ? parseInt(id, 10) : 0;
  }

  fetchProductsAndStocks(): void {
    fetch(`http://localhost:8086/stocks/getStocksByEnterprise/${this.idEnterprise}`)
      .then(res => res.json())
      .then((stocks: any[]) => {
        this.stocks = stocks;
        return Promise.all(
          stocks.map(stock =>
            fetch(`http://localhost:8084/products/${stock.idProduct}`)
              .then(res => res.json())
              .then(product => ({ ...product, quantity: stock.quantity }))
          )
        );
      })
      .then(products => {
        this.products = products;
        this.filteredStocks = [...this.products].filter(p => p.active);
        console.log(this.products);
        console.log(this.stocks);
      })
      .catch(err => console.error("Erreur lors de la récupération des données :", err));
  }

  filterStocks(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredStocks = this.products.filter(product =>
      product.name.toLowerCase().includes(term) ||
      product.type.toLowerCase().includes(term) ||
      (product.description || '').toLowerCase().includes(term)
    );
  }

  addProduct(): void {
    const body = {
      name: this.newProduct.name.trim(),
      type: this.newProduct.type.trim(),
      description: this.newProduct.description?.trim() || null,
      idEnterprise: this.idEnterprise
    };

    if (!body.name || !body.type || !this.idEnterprise || this.quantity == null) {
      alert("Tous les champs sont requis.");
      return;
    }

    fetch('http://localhost:8084/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    })
      .then(res => {
        if (!res.ok) throw new Error("Erreur lors de l'ajout du produit");
        return res.json();
      })
      .then((addedProduct) => {
        const stock = {
          idProduct: addedProduct.id,
          idEnterprise: this.idEnterprise,
          quantity: this.quantity
        };

        return fetch('http://localhost:8086/stocks', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(stock)
        }).then(res => {
          if (!res.ok) throw new Error("Erreur lors de l'ajout du stock");
          return res.json();
        }).then((stock) => {
          this.products.push({ ...addedProduct, quantity: this.quantity });
          this.filteredStocks.push({ ...addedProduct, quantity: this.quantity });
          this.stocks.push(stock);
          this.isAdding = false;
          this.newProduct = { name: '', type: '', description: '' };
          this.quantity = 0;
        });
      })
      .catch(err => alert(err.message));
  }

  async deleteProduct(product: any) {
    try {
      const stock = this.stocks.find(s => s.idProduct === product.id);
      if (!stock) {
        alert("Stock introuvable pour ce produit.");
        return;
      }

      const response = await fetch(`http://localhost:8084/products/updateActive/${product.id}/${stock.id}`, {
        method: 'PATCH',
      });

      if (!response.ok) throw new Error("Erreur lors de la suppression du produit");

      this.products = this.products.filter(p => p.id !== product.id);
      this.filteredStocks = this.filteredStocks.filter(p => p.id !== product.id);
      this.stocks = this.stocks.filter(s => s.idProduct !== product.id);
    } catch (err: any) {
      console.error("Erreur suppression :", err);
      alert(err.message || "Une erreur est survenue");
    }
  }

  startEdit(product: any): void {
    this.editingProductId = product.id;
    this.editProductData = { ...product };
  }

  cancelEdit(): void {
    this.editingProductId = null;
    this.editProductData = { name: '', type: '', description: '', quantity: 0 };
  }

  saveEdit(): void {
    fetch(`http://localhost:8084/products/updateProduct/${this.editProductData.id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(this.editProductData)
    })
      .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la modification du produit");
        return res.json();
      })
      .then((updatedProduct) => {
        const index = this.products.findIndex(p => p.id === updatedProduct.id);
        if (index !== -1) {
          this.products[index] = {
            ...updatedProduct,
            quantity: this.editProductData.quantity
          };
        }

        const stock = this.stocks.find(s => s.idProduct === updatedProduct.id);
        if (!stock) throw new Error("Stock introuvable pour ce produit.");

        return fetch(`http://localhost:8086/stocks/updateStock/${stock.id}`, {
          method: 'PATCH',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ quantity: this.editProductData.quantity })
        })
          .then(res => {
            if (!res.ok) throw new Error("Erreur lors de la mise à jour du stock");
            return res.json();
          })
          .then(updatedStock => {
            stock.quantity = updatedStock.quantity;
            this.products[index].quantity = updatedStock.quantity;
            this.filteredStocks = this.products.filter(p => p.active);
            this.cancelEdit();
          });
      })
      .catch(err => alert(err.message));
  }
}
