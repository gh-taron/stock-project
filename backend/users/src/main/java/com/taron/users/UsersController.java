package com.taron.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taron.users.models.FavoriteSupplier;
import com.taron.users.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService service;

    public UsersController(UsersService service){
        this.service = service;
    }

    @PostMapping()
    public User createOne(@RequestBody User user){
        return this.service.createOne(user);
    }

    @GetMapping("/existsByPhone/{phone}")
    public boolean existsByPhoneNumber(@PathVariable String phone){
        return this.service.existsByPhoneNumber(phone);
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable int id){
        return this.service.findById(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return this.service.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public Optional<User> findByEmail(@PathVariable String email){
       return this.service.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllByEnterprise/{idEnterprise}")
    public List<User> getAllByEnterprise(@PathVariable int idEnterprise){
        return this.service.getAllByEnterprise(idEnterprise);
    }

    @PatchMapping("/updateWithPhoto")
    public ResponseEntity<User> updateWithPhoto(
            @RequestParam("user") String userJson,
            @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userJson, User.class);

        if (photo != null && !photo.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path imagePath = Paths.get(System.getProperty("user.dir"), "backend", "users", "uploads").resolve(fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, photo.getBytes());

            user.setPhotoUrl("/uploads/" + fileName);
        }

        User updated = service.createOne(user);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/favoriteSupplierIds/{idEnterprise}")
    public List<Integer> getFavoriteSupplierIds(@PathVariable Integer idEnterprise) {
        return service.getFavoriteSupplierIds(idEnterprise);
    }

    @PostMapping("/favoriteSuppliers/addOne")
    public ResponseEntity<?> addFavoriteSupplier(@RequestBody FavoriteSupplier fav) {
        service.addFavorite(fav.getIdEnterprise(), fav.getIdSupplier());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favoriteSuppliers/deleteOne")
    public ResponseEntity<?> removeFavoriteSupplier(@RequestBody FavoriteSupplier fav) {
        service.removeFavorite(fav.getIdEnterprise(), fav.getIdSupplier());
        return ResponseEntity.noContent().build();
    }
}
