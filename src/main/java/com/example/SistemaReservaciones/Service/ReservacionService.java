package com.example.SistemaReservaciones.Service;

import com.example.SistemaReservaciones.Dto.Cliente;
import com.example.SistemaReservaciones.Dto.Habitacion;
import com.example.SistemaReservaciones.Dto.Reservacion;
import com.example.SistemaReservaciones.Exceptions.BadRequestException;
import com.example.SistemaReservaciones.Exceptions.ResourceNotFoundException;
import com.example.SistemaReservaciones.Repositories.ClienteRepository;
import com.example.SistemaReservaciones.Repositories.HabitacionRepository;
import com.example.SistemaReservaciones.Repositories.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservacionService {

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Reservacion> obtenerTodas() {
        return reservacionRepository.findAll();
    }

    public Reservacion guardarReservacion(Reservacion reservacion) {
        // 1. Validar que la habitación exista
        Habitacion habitacionExistente = habitacionRepository.findById(reservacion.getHabitacion().getNumero())
                .orElse(null);

        Cliente clienteExistente = clienteRepository.findById(reservacion.getCliente().getId()).orElse(null);

        if ( clienteExistente == null){
            throw new ResourceNotFoundException("El cliente con ID " + reservacion.getCliente().getId() + " no existe.");
        }


        if (habitacionExistente == null) {
            throw new ResourceNotFoundException("La habitación con número " + reservacion.getHabitacion().getNumero() +
                    " no existe."); // Error: No existe la habitación
        }

        // 2. 🛡️ ¡EL ESCUDO DE DISPONIBILIDAD!
        // Buscamos si esa habitación ya está ocupada en ese rango de fechas
       boolean estaOcupada = reservacionRepository.verificarTraslape(
               habitacionExistente,
               reservacion.getFechaEntrada(),
               reservacion.getFechaSalida()
       );

        if (estaOcupada) {
            // Si ya está ocupada, retornamos null para que el controlador avise del choque
            throw new BadRequestException("La habitación ya se encuentra ocupada en el rango de fechas seleccionado.");
        }

        Double precioHabitacion = habitacionExistente.getPrecio();
        long dias = java.time.temporal.ChronoUnit.DAYS.between(reservacion.getFechaEntrada(),reservacion.getFechaSalida());

        reservacion.setPrecioTotal(precioHabitacion * dias);
        // 3. Si pasó ambas pruebas, guardamos con éxito
        reservacion.setCliente(clienteExistente);
        reservacion.setHabitacion(habitacionExistente);
        return reservacionRepository.save(reservacion);
    }

    public boolean eliminarReservacion(Long id) {
        if (reservacionRepository.existsById(id)) {
            reservacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}