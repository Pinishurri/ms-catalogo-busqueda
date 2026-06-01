package com.perfulandia.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.catalogo.dto.ProductoDTO;
import com.perfulandia.catalogo.model.Producto;
import com.perfulandia.catalogo.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalogo")
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService servicioProductos;

    // El cliente escribe una palabra y busca productos por nombre
    // Ejemplo: /api/catalogo/buscar/rose
    @GetMapping("/buscar/{palabraBuscada}")
    public ResponseEntity<List<Producto>> buscarProductos(@PathVariable String palabraBuscada) {
        log.info("Solicitud de busqueda con la palabra: {}", palabraBuscada);
        List<Producto> resultados = servicioProductos.buscarPorNombre(palabraBuscada);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    // Filtra todos los productos de una categoria especifica
    // Ejemplo: /api/catalogo/categoria/Perfumes
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> filtrarPorCategoria(@PathVariable String categoria) {
        log.info("Solicitud de filtro por categoria: {}", categoria);
        List<Producto> resultados = servicioProductos.filtrarPorCategoria(categoria);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    // Devuelve el detalle completo de un producto especifico
    // @PathVariable recibe el id desde la URL
    @GetMapping("/producto/{id}")
    public ResponseEntity<Producto> obtenerDetalle(@PathVariable Long id) {
        log.info("Solicitud de detalle del producto con id: {}", id);
        Optional<Producto> producto = servicioProductos.obtenerDetalle(id);

        if (producto.isPresent()) {
            return new ResponseEntity<>(producto.get(), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // Devuelve la lista de todas las categorias disponibles
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        log.info("Solicitud para listar todas las categorias");
        List<String> categorias = servicioProductos.listarCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    // El administrador agrega un producto nuevo al catalogo
    // @Valid activa las validaciones del DTO
    @PostMapping("/agregar")
    public ResponseEntity<Producto> agregarProducto(@Valid @RequestBody ProductoDTO datosProducto) {
        log.info("Solicitud para agregar producto: {}", datosProducto.getNombre());

        // Convertimos el DTO a una entidad Producto
        Producto productoNuevo = new Producto();
        productoNuevo.setNombre(datosProducto.getNombre());
        productoNuevo.setDescripcion(datosProducto.getDescripcion());
        productoNuevo.setCategoria(datosProducto.getCategoria());
        productoNuevo.setPrecio(datosProducto.getPrecio());

        Producto productoGuardado = servicioProductos.agregarProducto(productoNuevo);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED); // 201
    }

    // El administrador modifica los datos de un producto existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO datosActualizados) {
        log.info("Solicitud para actualizar producto con id: {}", id);

        // Convertimos el DTO a una entidad Producto con los datos nuevos
        Producto datosNuevos = new Producto();
        datosNuevos.setNombre(datosActualizados.getNombre());
        datosNuevos.setDescripcion(datosActualizados.getDescripcion());
        datosNuevos.setCategoria(datosActualizados.getCategoria());
        datosNuevos.setPrecio(datosActualizados.getPrecio());

        Optional<Producto> productoActualizado = servicioProductos
                .actualizarProducto(id, datosNuevos);

        if (productoActualizado.isPresent()) {
            return new ResponseEntity<>(productoActualizado.get(), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // Devuelve el catalogo completo con todos los productos
    @GetMapping("/todos")
    public ResponseEntity<List<Producto>> listarTodos() {
        log.info("Solicitud para listar todos los productos");
        List<Producto> productos = servicioProductos.listarTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}