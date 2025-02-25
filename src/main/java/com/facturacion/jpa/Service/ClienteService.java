package com.facturacion.jpa.Service;

import com.facturacion.jpa.entity.Cliente;
import com.facturacion.jpa.Repositorio.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClientRepository clienteRepository;

    @Autowired
    public ClienteService(ClientRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    // Método para buscar cliente por ID
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Método para guardar cliente
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Método para eliminar cliente
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}


