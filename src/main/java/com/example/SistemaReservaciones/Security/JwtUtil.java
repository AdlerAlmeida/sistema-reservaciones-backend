package com.example.SistemaReservaciones.Security;

import com.example.SistemaReservaciones.Dto.Cliente;
import io.jsonwebtoken.Claims;
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

    public String extraerMail(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(CLAVE_SECRETA)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token, Cliente cliente){
        try {
            final String mailToken = extraerMail(token);

            Date expiracion = Jwts.parserBuilder()
                    .setSigningKey(CLAVE_SECRETA)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            boolean tokenExpirado = expiracion.before(new Date());

            return (mailToken.equals(cliente.getMail()) && !tokenExpirado);
        } catch (Exception e) {
            return false;
        }
    }
}
