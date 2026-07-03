package com.example.SistemaReservaciones.Service;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Dto.LoginRequest;
import com.example.SistemaReservaciones.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public void guardarCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }

    public Cliente iniciarSesion(LoginRequest loginData){
        Optional<Cliente> clienteEncontrado = clienteRepository.findByMail(loginData.getMail());

        if(!clienteEncontrado.isPresent()){
            throw new RuntimeException("El correo electrónico no está registrado.");
        }

        Cliente cliente = clienteEncontrado.get();

        if(!loginData.getContrasena().equals(cliente.getContrasena())){
            throw new RuntimeException("La contraseña es incorrecta.");
        }

        return cliente;
    }
}
