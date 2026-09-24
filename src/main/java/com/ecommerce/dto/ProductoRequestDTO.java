package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 50, message = "La categoria no debe superar 50 caracteres")
    private String categoria;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 enteros y 2 decimales")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no debe superar 50 caracteres")
    private String marca;

    @NotBlank(message = "El codigo de barras es obligatorio")
    @Pattern(regexp = "\\d{8,20}", message = "El codigo de barras debe tener entre 8 y 20 digitos")
    private String codigoBarras;

    public ProductoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
}
