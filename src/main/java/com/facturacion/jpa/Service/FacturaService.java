package com.facturacion.jpa.Service;

import com.facturacion.jpa.Repositorio.ClientRepository;
import com.facturacion.jpa.Repositorio.FacturaRepository;
import com.facturacion.jpa.Repositorio.ProductRepository;
import com.facturacion.jpa.entity.Cliente;
import com.facturacion.jpa.entity.DetalleFactura;
import com.facturacion.jpa.entity.Product;
import com.facturacion.jpa.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Método para crear una factura
    public FacturaResponse crearFactura(Factura factura) {
        FacturaResponse response = new FacturaResponse();

        // 1️⃣ Validar que el cliente exista
        Optional<Cliente> clienteOpt = clientRepository.findById(factura.getCliente().getId());
        if (!clienteOpt.isPresent()) {
            response.setError("El cliente no existe.");
            return response;
        }

        // 2️⃣ Validar productos y stock
        double totalPrecio = 0;
        int totalCantidad = 0;

        for (DetalleFactura detalle : factura.getDetalles()) {
            Optional<Product> productOpt = productRepository.findById(detalle.getProduct().getId());
            if (!productOpt.isPresent()) {
                response.setError("El producto con ID " + detalle.getProduct().getId() + " no existe.");
                return response;
            }

            Product producto = productOpt.get();

            // Validar stock
            if (producto.getStock() < detalle.getCantidad()) {
                response.setError("No hay suficiente stock para el producto: " + producto.getNombre());
                return response;
            }

            // Calcular precio total y cantidad total
            totalPrecio += detalle.getCantidad() * detalle.getPrecioUnitario();
            totalCantidad += detalle.getCantidad();

            // Reducir el stock del producto
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productRepository.save(producto);
        }

        // 3️⃣ Obtener la fecha desde el servicio externo o usar la fecha local
        String fechaStr = obtenerFechaActual();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter);

        // 4️⃣ Guardar la factura con los datos calculados
        factura.setFecha(fecha);
        facturaRepository.save(factura);

        // 5️⃣ Construir la respuesta final
        response.setFactura(factura);
        response.setFecha(fecha.toString());
        response.setTotalPrecio(totalPrecio);
        response.setTotalCantidad(totalCantidad);
        response.setMensaje("Factura creada exitosamente.");

        return response;
    }

    // Método para obtener la fecha desde el servicio REST externo
    private String obtenerFechaActual() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://worldclockapi.com/api/json/utc/now";
            WorldClockResponse worldClockResponse = restTemplate.getForObject(url, WorldClockResponse.class);
            return worldClockResponse.getCurrentDateTime();
        } catch (Exception e) {
            // Si falla el servicio, usar la fecha local
            return LocalDateTime.now().toString();
        }
    }
}
