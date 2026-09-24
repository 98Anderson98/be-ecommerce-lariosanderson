package com.ecommerce.dto;

import com.ecommerce.entity.Pedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String cliente,
        LocalDateTime fechaCompra,
        BigDecimal montoTotal,
        String estadoPedido,
        String metodoPago,
        List<DetallePedidoResponseDTO> detalles) {

    public static PedidoResponseDTO fromEntity(Pedido p) {
        return new PedidoResponseDTO(p.getId(), p.getCliente(), p.getFechaCompra(), p.getMontoTotal(),
                p.getEstadoPedido(), p.getMetodoPago(),
                p.getDetalles().stream().map(DetallePedidoResponseDTO::fromEntity).toList());
    }
}
