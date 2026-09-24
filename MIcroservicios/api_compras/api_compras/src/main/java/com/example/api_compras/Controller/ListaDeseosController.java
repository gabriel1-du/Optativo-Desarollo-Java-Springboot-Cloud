package com.example.api_compras.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//DTO
import com.example.api_compras.DTO.ListaDeseosDTO.getListaDeseosDTO;
import com.example.api_compras.DTO.ListaDeseosDTO.postListaDeseosDTO;
import com.example.api_compras.Service.ListaDeseosService;
import com.example.api_compras.Model.ListaDeseos;


@RestController 
@RequestMapping("/api/ListaDeseosApi")
public class ListaDeseosController {

    // inyeccion del servicio
    @Autowired
    private ListaDeseosService listaService;


    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<getListaDeseosDTO>> getAllCarrito() {

        List<getListaDeseosDTO> listas = listaService.getAllListaDeseos();
        return new ResponseEntity<>(listas, HttpStatus.OK);
    }


    @GetMapping("/{id_lista_deseos}")
    public ResponseEntity<?> getCarritoById(@PathVariable Long id_lista_deseos) {
        try {
            getListaDeseosDTO lista = listaService.getListaDeseosById(id_lista_deseos);
            return ResponseEntity.ok(lista);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get

    @PostMapping("/")
    public ResponseEntity<?> saveListaDeseos(@RequestBody postListaDeseosDTO postListaDTO) {
        try {
            getListaDeseosDTO save = listaService.saveListaDeseos(postListaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // metodos PUT
    @PutMapping("/{id_lista_deseos}")
    public ResponseEntity<?> putListaDeseos(@RequestBody ListaDeseos lista, @PathVariable Long id_lista_deseos) {
        try {
            getListaDeseosDTO lista_actualizada = listaService.putListaDeseos(lista, id_lista_deseos);
            return ResponseEntity.ok(lista_actualizada);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


       // metodos DELETE
    @DeleteMapping("/{id_listas_deseos}")
    public ResponseEntity<?> deleteListaDeseos(@PathVariable Long id_listas_deseos) {
        try {
            listaService.deleteListaDeseos(id_listas_deseos);

            return ResponseEntity.ok("Registro eliminado exitosamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/usuario/{id_lista_deseos}")
    public ResponseEntity<?> deleteListaDeseosByUsuario(@PathVariable Long id_usuario) {
        try {
            listaService.deleteListaDeseosByUsuario(id_usuario);
            return ResponseEntity.ok("Lista de deseos asociada al usuario eliminado exitosamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

}
