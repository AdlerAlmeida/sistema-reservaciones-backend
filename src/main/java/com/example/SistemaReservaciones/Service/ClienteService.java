package com.example.SistemaReservaciones.Service;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public void guardarCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }
}
