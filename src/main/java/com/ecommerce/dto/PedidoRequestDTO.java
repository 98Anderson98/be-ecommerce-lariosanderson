package com.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PedidoRequestDTO {

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    @Size(max = 100, message = "El cliente no debe superar 100 caracteres")
    private String cliente;

    @NotBlank(message = "El metodo de pago es obligatorio")
    @Pattern(regexp = "EFECTIVO|TARJETA|YAPE|PLIN|TRANSFERENCIA",
             message = "Metodo de pago invalido. Valores: EFECTIVO, TARJETA, YAPE, PLIN, TRANSFERENCIA")
    private String metodoPago;

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    @Valid
    private List<ItemPedidoRequestDTO> items;

    public PedidoRequestDTO() {}

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public List<ItemPedidoRequestDTO> getItems() { return items; }
    public void setItems(List<ItemPedidoRequestDTO> items) { this.items = items; }
}
