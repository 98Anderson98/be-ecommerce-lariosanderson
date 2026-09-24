package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EstadoPedidoRequestDTO {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "REGISTRADO|PAGADO|ENVIADO|ENTREGADO|CANCELADO",
             message = "Estado invalido. Valores: REGISTRADO, PAGADO, ENVIADO, ENTREGADO, CANCELADO")
    private String estado;

    public EstadoPedidoRequestDTO() {}

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
