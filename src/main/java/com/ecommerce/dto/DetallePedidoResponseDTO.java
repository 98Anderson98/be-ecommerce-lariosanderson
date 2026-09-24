package com.ecommerce.dto;

import com.ecommerce.entity.DetallePedido;
import java.math.BigDecimal;

public record DetallePedidoResponseDTO(
        Long id,
        Long productoId,
        String producto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal) {

    public static DetallePedidoResponseDTO fromEntity(DetallePedido d) {
        return new DetallePedidoResponseDTO(d.getId(), d.getProducto().getId(), d.getProducto().getNombre(),
                d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal());
    }
}
