package com.example.SistemaReservaciones.Security;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Repositories.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Conseguimos el encabezado llamado "Authorization" de la petición HTTP
        final String authorizationHeader = request.getHeader("Authorization");

        String mail = null;
        String jwt = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Cortamos la palabra "Bearer
            try {
                mail = jwtUtil.extraerMail(jwt); // Usamos tu molde para sacar el correo del usuario
            } catch (Exception e) {
                // Si el token está corrupto o mal firmado, saltará aquí
                logger.error("Error al extraer el mail del token: " + e.getMessage());
            }
        }

        // 3. Si encontramos un correo válido en el token, verificamos al cliente en la base de datos
        if (mail != null) {
            Optional<Cliente> clienteOpt = clienteRepository.findByMail(mail);

            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();

                // 4. ¡Llamamos a tu función de validación! Revisamos si el token es legítimo y no ha expirado
                if (jwtUtil.validarToken(jwt, cliente)) {
                    //  ¡Brazalete verificado! Guardamos al usuario de forma temporal en la petición
                    // para que el controlador sepa exactamente qué usuario está operando.
                    request.setAttribute("clienteLogueado", cliente);
                } else {
                    //  Token inválido o expirado
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Tu token no es válido o ya expiró.");
                    return; // Detiene la petición aquí, no la deja pasar al controlador
                }
            }
        }

        // 5. Si todo está en orden (o si es una ruta pública como el Login que no ocupa token),
        // el guardia se quita y deja que la petición siga su camino hacia el controlador.
        filterChain.doFilter(request, response);
    }
}
