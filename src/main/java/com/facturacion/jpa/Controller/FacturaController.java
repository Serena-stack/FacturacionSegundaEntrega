package com.facturacion.jpa.Controller;

import com.facturacion.jpa.Service.FacturaResponse;
import com.facturacion.jpa.Service.FacturaService;
import com.facturacion.jpa.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<Object> crearFactura(@RequestBody Factura factura) {
        FacturaResponse response = facturaService.crearFactura(factura);

        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response.getError());
        }

        return ResponseEntity.ok(response);
    }
}

