package com.facturacion.jpa.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.facturacion.jpa.entity.Cliente;

public interface ClientRepository extends JpaRepository<Cliente, Long> {
}

