package com.example.api_compras.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_compras.Model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
