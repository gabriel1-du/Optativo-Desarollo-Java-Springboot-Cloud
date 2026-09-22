package com.example.api_compras.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.Model.MedioPago;
import com.example.api_compras.Repository.MedioPagoRepository;
import com.example.api_compras.Service.MedioPagoService;

@Service 
public class MedioPagoServiceImpl implements MedioPagoService {

    // Inyeccion de repositorios y dependencias
    @Autowired
    private MedioPagoRepository medioPagoRepository;

    // metodos GET
    @Override
    public List<MedioPago> getAllMediosPago() {
        return medioPagoRepository.findAll();
    }

    @Override
    public MedioPago getMedioPagoById(Long id_medio_pago) {
        return medioPagoRepository.findById(id_medio_pago)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con id: " + id_medio_pago));
    }
    // --- FIN GET

    // metodos POST
    @Override
    public MedioPago saveMedioPago(MedioPago medioPago) {
        return medioPagoRepository.save(medioPago);
    }

    // metodos PUT
    @Override
    public MedioPago putMedioPago(MedioPago medioPago, Long id_medio_pago) {
        MedioPago medioPago_existente = medioPagoRepository.findById(id_medio_pago)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con id: " + id_medio_pago));

        medioPago_existente.setNombre_medio(medioPago.getNombre_medio());
        medioPago_existente.setActivo(medioPago.getActivo());

        return medioPagoRepository.save(medioPago_existente);
    }

    // metodos DELETE
    @Override
    public void deleteMedioPago(Long id_medio_pago) {
        MedioPago medioPago_eliminado = medioPagoRepository.findById(id_medio_pago)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con id: " + id_medio_pago));

        medioPagoRepository.delete(medioPago_eliminado);
    }

}
