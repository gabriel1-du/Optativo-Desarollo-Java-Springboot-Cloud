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

import com.example.api_compras.Model.MedioPago;
import com.example.api_compras.Service.MedioPagoService;

@RestController

@RequestMapping("/api/mediosPagoApi") // url de acceso
public class MediosPagoController {

    // inyeccion del servicio
    @Autowired
    private MedioPagoService medioPagoService;

    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<MedioPago>> getAllMediosPago() {
        List<MedioPago> mediosPago = medioPagoService.getAllMediosPago();
        return new ResponseEntity<>(mediosPago, HttpStatus.OK);
    }

    @GetMapping("/{id_medio_pago}")
    public ResponseEntity<?> getMedioPagoById(@PathVariable Long id_medio_pago) {
        try {
            MedioPago medioPago = medioPagoService.getMedioPagoById(id_medio_pago);
            return ResponseEntity.ok(medioPago);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get

    // metodos POST
    @PostMapping("/")
    public ResponseEntity<?> saveMedioPago(@RequestBody MedioPago medioPago) {
        try {
            MedioPago save = medioPagoService.saveMedioPago(medioPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos PUT
    @PutMapping("/{id_medio_pago}")
    public ResponseEntity<?> putMedioPago(@RequestBody MedioPago medioPago, @PathVariable Long id_medio_pago) {
        try {
            MedioPago medioPago_actualizado = medioPagoService.putMedioPago(medioPago, id_medio_pago);
            return ResponseEntity.ok(medioPago_actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos DELETE
    @DeleteMapping("/{id_medio_pago}")
    public ResponseEntity<?> deleteMedioPago(@PathVariable Long id_medio_pago) {
        try {
            medioPagoService.deleteMedioPago(id_medio_pago);
            return ResponseEntity.ok("Registro eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
