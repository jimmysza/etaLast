package main.service;

import main.entity.Cliente;
import java.util.List;

public interface ClienteService {

    Cliente registrarCliente(Cliente cliente);

    List<Cliente> findAll();
}
