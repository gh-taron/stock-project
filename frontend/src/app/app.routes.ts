import { Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user/user.component';
import { SupplierComponent } from './supplier/supplier.component';
import { SupplierFavoriteComponent } from './supplier-favorite/supplier-favorite.component';
import { StockComponent } from './stock/stock.component';
import { ProductsSupplierComponent } from './products-supplier/products-supplier.component';
export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: "login", component : LoginComponent},
    { path: "register", component : RegisterComponent },
    { path: "user", component : UserComponent},
    { path: "supplier", component : SupplierComponent},
    { path: "favorite-suppliers", component : SupplierFavoriteComponent},
    { path: "stock", component : StockComponent},
    { path: 'products-supplier/:id', component: ProductsSupplierComponent },
]