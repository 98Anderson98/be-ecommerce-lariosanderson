package com.ecommerce.service;

import com.ecommerce.dto.EstadoPedidoRequestDTO;
import com.ecommerce.dto.ItemPedidoRequestDTO;
import com.ecommerce.dto.PedidoRequestDTO;
import com.ecommerce.dto.PedidoResponseDTO;
import com.ecommerce.entity.DetallePedido;
import com.ecommerce.entity.Pedido;
import com.ecommerce.entity.Producto;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

        @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        Pedido pedido = new Pedido();
        pedido.setCliente(request.getCliente().trim());
        pedido.setMetodoPago(request.getMetodoPago());
        pedido.setEstadoPedido("REGISTRADO");

        BigDecimal montoTotal = BigDecimal.ZERO;
        Set<Long> productosVistos = new HashSet<>();

        for (ItemPedidoRequestDTO item : request.getItems()) {
            if (!productosVistos.add(item.getProductoId())) {
                throw new BadRequestException("El producto " + item.getProductoId()
                        + " esta repetido en el pedido. Agrupe la cantidad en un solo item");
            }

            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                throw new BadRequestException("Stock insuficiente para el producto: " + producto.getNombre()
                        + ". Stock disponible: " + producto.getStock() + ", solicitado: " + item.getCantidad());
            }

            producto.setStock(producto.getStock() - item.getCantidad());

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
            montoTotal = montoTotal.add(subtotal);

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            pedido.addDetalle(detalle);
        }

        pedido.setMontoTotal(montoTotal);
        return PedidoResponseDTO.fromEntity(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.listarConDetallesJPQL().stream().map(PedidoResponseDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPorId(Long id) {
        return PedidoResponseDTO.fromEntity(buscarEntidad(id));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPorCliente(String cliente) {
        return pedidoRepository.buscarPorClienteConDetallesJPQL(cliente).stream()
                .map(PedidoResponseDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstadoNamed(estado.toUpperCase()).stream()
                .map(PedidoResponseDTO::fromEntity).toList();
    }

    @Transactional
    public PedidoResponseDTO cambiarEstado(Long id, EstadoPedidoRequestDTO dto) {
        Pedido pedido = buscarEntidad(id);
        if ("CANCELADO".equals(pedido.getEstadoPedido()) || "ENTREGADO".equals(pedido.getEstadoPedido())) {
            throw new BadRequestException("El pedido " + id + " ya esta " + pedido.getEstadoPedido() + " y no puede cambiar de estado");
        }
        if ("CANCELADO".equals(dto.getEstado())) {
            pedido.getDetalles().forEach(d ->
                    d.getProducto().setStock(d.getProducto().getStock() + d.getCantidad()));
        }
        pedido.setEstadoPedido(dto.getEstado());
        return PedidoResponseDTO.fromEntity(pedido);
    }

    private Pedido buscarEntidad(Long id) {
        return pedidoRepository.buscarPorIdConDetallesJPQL(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con el ID: " + id));
    }
}
