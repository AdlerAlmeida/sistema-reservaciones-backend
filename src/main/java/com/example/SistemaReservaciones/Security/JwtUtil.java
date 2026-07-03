package com.example.SistemaReservaciones.Security;

import com.example.SistemaReservaciones.Dto.Cliente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key CLAVE_SECRETA = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long TIEMPO_EXPIRACION = 86400000;

    public String generarToken(Cliente cliente){
        return Jwts.builder()
                .setSubject(cliente.getMail())
                .claim("id", cliente.getId())
                .claim("nombre", cliente.getNombre())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(CLAVE_SECRETA)
                .compact();
    }
}
