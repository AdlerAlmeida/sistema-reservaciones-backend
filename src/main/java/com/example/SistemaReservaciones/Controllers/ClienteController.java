package com.example.SistemaReservaciones.Controllers;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public String guardarCliente(@RequestBody @Valid Cliente cliente){
        clienteService.guardarCliente(cliente);
        return "Cliente guardado con exito!";

    }
}
