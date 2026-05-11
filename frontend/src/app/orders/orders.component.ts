import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-orders',
  standalone: false,
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: any[] = [];
  enterpriseType: string | null = localStorage.getItem('enterpriseType') || sessionStorage.getItem('enterpriseType');
  idUser: number = 0;
  idEnterprise: number = 0;

  readonly states = ['pending', 'confirmed', 'shipped', 'delivered', 'cancelled'];

  ngOnInit(): void {
    this.idUser = Number(localStorage.getItem('idUser') || sessionStorage.getItem('idUser'));
    this.idEnterprise = Number(localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise'));
    this.fetchOrders();
  }

  fetchOrders(): void {
    const url = this.enterpriseType === 'supplier'
      ? `http://localhost:8085/orders/getAllBySupplier/${this.idEnterprise}`
      : `http://localhost:8085/orders/getAllByBuyer/${this.idEnterprise}`;

    fetch(url)
      .then(res => res.json())
      .then(async (orders: any[]) => {
        this.orders = await Promise.all(orders.map(order => this.enrichOrder(order)));
      })
      .catch(err => console.error(err));
  }

  async enrichOrder(order: any): Promise<any> {
    const details = await fetch(`http://localhost:8085/orders/${order.id}/details`).then(res => res.json());

    const enrichedDetails = await Promise.all(
      details.map(async (d: any) => {
        const product = await fetch(`http://localhost:8084/products/${d.idProduct}`).then(res => res.json());
        return { ...d, productName: product.name };
      })
    );

    const contactId = this.enterpriseType === 'supplier' ? order.idBuyer : order.idSupplier;
    const enterprise = await fetch(`http://localhost:8083/enterprises/${contactId}`).then(res => res.json());
    const contactName = enterprise.name;

    return { ...order, details: enrichedDetails, contactName };
  }

  updateState(order: any): void {
    fetch(`http://localhost:8085/orders/${order.id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ state: order.state })
    })
      .then(res => res.json())
      .then(updated => order.state = updated.state)
      .catch(() => alert('Erreur lors de la mise à jour du statut'));
  }

  getStateBadgeClass(state: string): string {
    switch (state) {
      case 'pending':   return 'badge bg-warning text-dark';
      case 'confirmed': return 'badge bg-primary';
      case 'shipped':   return 'badge bg-info text-dark';
      case 'delivered': return 'badge bg-success';
      case 'cancelled': return 'badge bg-danger';
      default:          return 'badge bg-secondary';
    }
  }
}
