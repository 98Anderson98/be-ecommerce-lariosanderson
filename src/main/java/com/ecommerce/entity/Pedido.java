package com.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NamedQueries({
    @NamedQuery(
        name = "Pedido.findByEstadoNamed",
        query = "SELECT p FROM Pedido p WHERE p.estadoPedido = :estado ORDER BY p.fechaCompra DESC"
    )
})
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String cliente;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;

    @Column(name = "monto_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "estado_pedido", nullable = false, length = 20)
    private String estadoPedido;

    @Column(name = "metodo_pago", nullable = false, length = 20)
    private String metodoPago;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {
        this.fechaCompra = LocalDateTime.now();
        this.estadoPedido = "REGISTRADO";
    }

    public void addDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public String getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}
