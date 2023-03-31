package com.example.clase9webservice.controller;

import com.example.clase9webservice.entity.Product;
import com.example.clase9webservice.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @GetMapping("/product")
    public List<Product> listarProductos() {
        return productRepository.findAll();
    }

    final ProductRepository productRepository;

    public ProductoController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerProductoPorId(
            @PathVariable("id") String idStr) {


        HashMap<String,Object> responseJson = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Product> optProduct = productRepository.findById(id);
            if (optProduct.isPresent()) {
                responseJson.put("result","success");
                responseJson.put("product",optProduct.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg","Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            responseJson.put("msg","el ID debe ser un número entero positivo");
        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }






}

