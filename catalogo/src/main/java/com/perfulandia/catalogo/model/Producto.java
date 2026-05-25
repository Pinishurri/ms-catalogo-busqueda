package com.perfulandia.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Le dice a Spring que esta clase representa una tabla en la base de datos
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    // Clave primaria, se genera automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    // Nombre del producto, no puede llegar vacio
    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombre;

    // Descripcion del producto
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false, length = 500)
    private String descripcion;

    // Categoria a la que pertenece el producto, ej: "Perfumes", "Cremas"
    @NotBlank(message = "La categoria no puede estar vacia")
    @Column(nullable = false, length = 100)
    private String categoria;

    // Precio del producto, no puede ser negativo ni cero
    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    private Double precio;
}