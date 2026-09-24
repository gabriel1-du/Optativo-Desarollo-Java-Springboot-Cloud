package com.example.api_compras.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.api_compras.Model.Carrito;

import jakarta.transaction.Transactional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Buscar carrito por el id del usuario
    @Query("SELECT c FROM Carrito c WHERE c.id_usuario = :id_usuario")
    Optional<Carrito> findById_usuario(Long id_usuario);

    // Borrar directamente por id_usuario
    @Modifying
    @Transactional
    @Query("DELETE FROM Carrito c WHERE c.id_usuario = :id_usuario")
    void deleteById_usuario(Long id_usuario);

}
