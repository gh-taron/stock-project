import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsSupplierComponent } from './products-supplier.component';

describe('ProductsSupplierComponent', () => {
  let component: ProductsSupplierComponent;
  let fixture: ComponentFixture<ProductsSupplierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductsSupplierComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
