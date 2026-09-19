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

import com.example.api_compras.DTO.CarritoDTO.getCarritoDTO;
import com.example.api_compras.DTO.CarritoDTO.postCarritoDTO;
import com.example.api_compras.Model.Carrito;
import com.example.api_compras.Service.CarritoService;

@RestController
@RequestMapping("/api/carritosApi") // url de acceso
public class CarritoController {

    // inyeccion del servicio
    @Autowired
    private CarritoService carritoService;


    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<getCarritoDTO>> getAllCarrito() {
        List<getCarritoDTO> carritos = carritoService.getAllCarrito();
        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

    @GetMapping("/{id_carrito}")
    public ResponseEntity<?> getCarritoById(@PathVariable Long id_carrito) {
        try {
            getCarritoDTO carrito = carritoService.getCarritoById(id_carrito);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get


    // metodos POST
    @PostMapping("/")
    public ResponseEntity<?> saveCarrito(@RequestBody postCarritoDTO postCarritoDTO) {
        try {
            getCarritoDTO save = carritoService.saveCarrito(postCarritoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // metodos PUT
    @PutMapping("/{id_carrito}")
    public ResponseEntity<?> putCarrito(@RequestBody Carrito carrito, @PathVariable Long id_carrito) {
        try {
            getCarritoDTO carrito_actualizado = carritoService.putCarrito(carrito, id_carrito);
            return ResponseEntity.ok(carrito_actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // metodos DELETE
    @DeleteMapping("/{id_carrito}")
    public ResponseEntity<?> deleteCarrito(@PathVariable Long id_carrito) {
        try {
            carritoService.deleteCarrito(id_carrito);
            return ResponseEntity.ok("Registro eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
