import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';
import { AppComponent } from './app/app.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { UserComponent } from './user/user.component';
import { SupplierComponent } from './supplier/supplier.component';
import { SupplierFavoriteComponent } from './supplier-favorite/supplier-favorite.component';
import { StockComponent } from './stock/stock.component';
import { ProductsSupplierComponent } from './products-supplier/products-supplier.component';
import { OrdersComponent } from './orders/orders.component';
import { MatDialogModule } from '@angular/material/dialog';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    HomepageComponent,
    UserComponent,
    SupplierComponent,
    SupplierFavoriteComponent,
    StockComponent,
    ProductsSupplierComponent,
    OrdersComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    MatDialogModule

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

