package com.perfulandia.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia.catalogo.model.Producto;


// <Producto, Long>  dice con qué clase trabaja y el tipo de dato de su clave primaria
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Busca productos que contengan la palabra buscada en el nombre
    //El IgnoreCase significa que no importa si el cliente escribe "ROSE", "rose" o "Rose"
    List<Producto> findByNombreContainingIgnoreCase(String palabraBuscada);

    List<Producto> findByCategoria(String categoria);
}