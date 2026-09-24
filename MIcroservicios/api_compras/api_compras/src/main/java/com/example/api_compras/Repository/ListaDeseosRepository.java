package com.example.api_compras.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.api_compras.Model.ListaDeseos;

import jakarta.transaction.Transactional;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {
    

    @Query("SELECT l FROM ListaDeseos l WHERE l.id_usuario = :id_usuario")
    Optional<ListaDeseos> findById_lista(Long id_usuario);


    @Modifying
    @Transactional
    @Query("DELETE FROM ListaDeseos l WHERE l.id_usuario = :id_usuario")
    void deleteById_usuario(Long id_usuario);


}
