package com.perfulandia.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.catalogo.model.Producto;
import com.perfulandia.catalogo.repository.ProductoRepository;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository repositorioProductos;

    
    // BUSCAR PRODUCTOS POR NOMBRE
    // El cliente escribe una palabra y devuelve todos los
    // productos que la contengan en el nombre
    
    public List<Producto> buscarPorNombre(String palabraBuscada) {
        log.info("Buscando productos con la palabra: {}", palabraBuscada);
        List<Producto> resultados = repositorioProductos
                .findByNombreContainingIgnoreCase(palabraBuscada);
        log.info("Se encontraron {} productos", resultados.size());
        return resultados;
    }

    
    // FILTRAR POR CATEGORIA
    // Devuelve todos los productos de una categoria especifica
    
    public List<Producto> filtrarPorCategoria(String categoria) {
        log.info("Filtrando productos por categoria: {}", categoria);
        List<Producto> resultados = repositorioProductos.findByCategoria(categoria);
        log.info("Se encontraron {} productos en la categoria {}", resultados.size(), categoria);
        return resultados;
    }

   
    // VER DETALLE DE UN PRODUCTO
    // Busca un producto por su id y lo devuelve completo
   
    public Optional<Producto> obtenerDetalle(Long idProducto) {
        log.info("Buscando detalle del producto con id: {}", idProducto);
        Optional<Producto> producto = repositorioProductos.findById(idProducto);
        if (producto.isEmpty()) {
            log.warn("No se encontro producto con id: {}", idProducto);
        }
        return producto;
    }

 
    // LISTAR CATEGORIAS
    // Devuelve todas las categorias disponibles sin repetir
    
    public List<String> listarCategorias() {
        log.info("Listando todas las categorias disponibles");

        // findAll() trae todos los productos de la base de datos
        // .stream() convierte la lista en un flujo para procesarla uno por uno
        // .map(Producto::getCategoria) de cada producto agarra solo la categoria
        // .distinct() elimina las categorias repetidas
        // .toList() convierte el resultado de vuelta a una lista normal
        return repositorioProductos.findAll()
                .stream()
                .map(Producto::getCategoria)
                .distinct()
                .toList();
    }

   
    // AGREGAR PRODUCTO AL CATALOGO
    // El administrador agrega un producto nuevo al catalogo
    
    public Producto agregarProducto(Producto productoNuevo) {
        log.info("Agregando producto nuevo al catalogo: {}", productoNuevo.getNombre());
        Producto productoGuardado = repositorioProductos.save(productoNuevo);
        log.info("Producto agregado correctamente con id: {}", productoGuardado.getIdProducto());
        return productoGuardado;
    }

    
    // ACTUALIZAR PRODUCTO DEL CATALOGO
    // El administrador modifica los datos de un producto existente
   
    public Optional<Producto> actualizarProducto(Long idProducto, Producto datosNuevos) {
        log.info("Actualizando producto con id: {}", idProducto);
        Optional<Producto> busqueda = repositorioProductos.findById(idProducto);

        if (busqueda.isEmpty()) {
            log.warn("No se encontro producto con id: {}", idProducto);
            return Optional.empty();
        }

        // Tomamos el producto existente y le actualizamos los datos
        Producto productoExistente = busqueda.get();
        productoExistente.setNombre(datosNuevos.getNombre());
        productoExistente.setDescripcion(datosNuevos.getDescripcion());
        productoExistente.setCategoria(datosNuevos.getCategoria());
        productoExistente.setPrecio(datosNuevos.getPrecio());

        Producto productoActualizado = repositorioProductos.save(productoExistente);
        log.info("Producto actualizado correctamente: {}", productoActualizado.getNombre());
        return Optional.of(productoActualizado);
    }

  
    // LISTAR TODOS LOS PRODUCTOS
    // Devuelve el catalogo completo
  
    public List<Producto> listarTodos() {
        log.info("Listando todos los productos del catalogo");
        return repositorioProductos.findAll();
    }
}