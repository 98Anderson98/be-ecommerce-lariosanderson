package com.ecommerce.controller;

import com.ecommerce.dto.EstadoPedidoRequestDTO;
import com.ecommerce.dto.PedidoRequestDTO;
import com.ecommerce.dto.PedidoResponseDTO;
import com.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO request) {
        return new ResponseEntity<>(service.crearPedido(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<PedidoResponseDTO>> porCliente(@PathVariable String cliente) {
        return ResponseEntity.ok(service.buscarPorCliente(cliente));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoPedidoRequestDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto));
    }
}
