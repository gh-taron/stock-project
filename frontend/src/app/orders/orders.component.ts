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

  readonly states = ['pending', 'confirmed', 'shipped', 'delivered', 'cancelled', 'return_requested'];

  confirmModal: { open: boolean, title: string, message: string, confirmLabel: string, confirmClass: string, onConfirm: () => void } = {
    open: false,
    title: '',
    message: '',
    confirmLabel: 'Confirmer',
    confirmClass: 'btn-primary',
    onConfirm: () => {}
  };

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

  cancelOrder(order: any): void {
    this.openConfirm({
      title: 'Annuler la commande',
      message: `Êtes-vous sûr de vouloir annuler la commande #${order.id} ?`,
      confirmLabel: 'Oui, annuler',
      confirmClass: 'btn-danger',
      onConfirm: () => {
        order.state = 'cancelled';
        this.updateState(order);
      }
    });
  }

  returnOrder(order: any): void {
    this.openConfirm({
      title: 'Demander un retour',
      message: `Souhaitez-vous demander un retour pour la commande #${order.id} ?`,
      confirmLabel: 'Envoyer la demande',
      confirmClass: 'btn-warning',
      onConfirm: () => {
        fetch(`http://localhost:8085/orders/${order.id}`, {
          method: 'PATCH',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ state: 'return_requested' })
        })
          .then(res => res.json())
          .then(updated => order.state = updated.state)
          .catch(() => alert('Erreur lors de la demande de retour'));
      }
    });
  }

  openConfirm(opts: { title: string, message: string, confirmLabel: string, confirmClass: string, onConfirm: () => void }) {
    this.confirmModal = { open: true, ...opts };
  }

  closeConfirm() {
    this.confirmModal.open = false;
  }

  confirmAction() {
    this.confirmModal.onConfirm();
    this.closeConfirm();
  }

  getStateBadgeClass(state: string): string {
    switch (state) {
      case 'pending':   return 'badge bg-warning text-dark';
      case 'confirmed': return 'badge bg-primary';
      case 'shipped':   return 'badge bg-info text-dark';
      case 'delivered': return 'badge bg-success';
      case 'cancelled': return 'badge bg-danger';
      case 'return_requested': return 'badge bg-warning text-dark';
      default:          return 'badge bg-secondary';
    }
  }
}
