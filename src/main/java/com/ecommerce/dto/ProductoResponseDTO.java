package com.ecommerce.dto;

import com.ecommerce.entity.Producto;
import java.math.BigDecimal;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer stock,
        String marca,
        String codigoBarras) {

    public static ProductoResponseDTO fromEntity(Producto p) {
        return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getCategoria(),
                p.getPrecio(), p.getStock(), p.getMarca(), p.getCodigoBarras());
    }
}
