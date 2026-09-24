package com.ecommerce.controller;

import com.ecommerce.dto.ProductoRequestDTO;
import com.ecommerce.dto.ProductoResponseDTO;
import com.ecommerce.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> porCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<ProductoResponseDTO>> porMarca(@PathVariable String marca) {
        return ResponseEntity.ok(service.buscarPorMarca(marca));
    }

    @GetMapping("/codigo/{codigoBarras}")
    public ResponseEntity<ProductoResponseDTO> porCodigoBarras(@PathVariable String codigoBarras) {
        return ResponseEntity.ok(service.buscarPorCodigoBarras(codigoBarras));
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> porRangoPrecio(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(service.buscarPorRangoPrecio(min, max));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> porNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @GetMapping("/buscar-avanzado")
    public ResponseEntity<List<ProductoResponseDTO>> buscarAvanzado(@RequestParam String keyword, @RequestParam Integer stockMin) {
        return ResponseEntity.ok(service.buscarAvanzado(keyword, stockMin));
    }
}
