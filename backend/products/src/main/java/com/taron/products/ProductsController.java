package com.taron.products;

import com.taron.products.models.Product;
import com.taron.products.repositories.ProductsRepository;
import com.taron.stocks.models.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService service;


    public ProductsController(ProductsService service) {
        this.service = service;
    }


    @GetMapping("/getAll")
    public List<Product> getAll(){
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id){
        return this.service.getById(id);
    }

    @GetMapping("/getAllBySupplier/{id}")
    public List<Product> getAllBySupplier(@PathVariable int id){
        return this.service.getAllBySupplier(id);
    }

    @PatchMapping("/updateProduct/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product newProduct){
        Product product = this.service.getById(id);
        product.setType(newProduct.getType());
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        return this.service.createOne(product);
    }

    @PostMapping("")
    public ResponseEntity<?> createOne(@RequestBody Product product) {
        if (product.getName() == null || product.getName().isBlank() ||
                product.getType() == null || product.getType().isBlank() ||
                product.getDescription() == null || product.getIdSupplier() == null) {
            return ResponseEntity.badRequest().body("Tous les champs requis doivent être présents.");
        }

        Product created = this.service.createOne(product);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/updateActive/{idProduct}/{idStock}")
    public Product updateProductActive(@PathVariable int idProduct, @PathVariable int idStock){
        Product product = this.service.getById(idProduct);
        product.setActive(false);
        return this.service.updateProductActive(idStock, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne (@PathVariable Integer id) {
        this.service.deleteOne(id);
        return ResponseEntity.ok("Produit et stock supprimés.");
    }
}
