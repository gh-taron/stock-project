import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierFavoriteComponent } from './supplier-favorite.component';

describe('SupplierFavoriteComponent', () => {
  let component: SupplierFavoriteComponent;
  let fixture: ComponentFixture<SupplierFavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SupplierFavoriteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupplierFavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
