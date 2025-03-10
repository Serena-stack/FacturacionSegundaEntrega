package com.facturacion.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Product product; // 🔹 Nombre uniforme: product en lugar de producto

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;

    // Constructor vacío
    public DetalleFactura() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Product getProduct() { return product; } // 🔹 Nombre uniforme
    public void setProduct(Product product) {
        this.product = product;
        if (this.precioUnitario == null) { // 🔹 Solo asigna el precio la primera vez
            this.precioUnitario = product.getPrecio();
        }
        calcularSubtotal();
    }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }

    private void calcularSubtotal() {
        if (this.cantidad != null && this.precioUnitario != null) {
            this.subtotal = this.cantidad * this.precioUnitario;
        } else {
            this.subtotal = 0.0; // 🔹 Evita valores null en subtotal
        }
    }
}


