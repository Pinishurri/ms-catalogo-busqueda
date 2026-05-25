package com.perfulandia.catalogo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO para crear o actualizar un producto
// Solo acepta los campos necesarios, el idProducto lo genera la base de datos sola
@Data
public class ProductoDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    // Categoria del producto, ej: "Perfumes", "Cremas", "Accesorios"
    @NotBlank(message = "La categoria no puede estar vacia")
    private String categoria;

    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
    private Double precio;
}