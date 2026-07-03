package com.example.SistemaReservaciones.Controllers;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Dto.LoginRequest;
import com.example.SistemaReservaciones.Dto.Reservacion;
import com.example.SistemaReservaciones.Security.JwtUtil;
import com.example.SistemaReservaciones.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/crear")
    public String guardarCliente(@RequestBody @Valid Cliente cliente){
        clienteService.guardarCliente(cliente);
        return "Cliente guardado con exito!";

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginData){
        try{
            Cliente clienteLogueado = clienteService.iniciarSesion(loginData);

            String token = jwtUtil.generarToken(clienteLogueado);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("cliente", clienteLogueado);
            respuesta.put("token", token);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
