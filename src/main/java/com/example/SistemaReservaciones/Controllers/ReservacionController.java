package com.example.SistemaReservaciones.Controllers;

import com.example.SistemaReservaciones.Dto.Reservacion;
import com.example.SistemaReservaciones.Repositories.ClienteRepository;
import com.example.SistemaReservaciones.Repositories.HabitacionRepository;
import com.example.SistemaReservaciones.Service.ClienteService;
import com.example.SistemaReservaciones.Service.ReservacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservacionController {

    @Autowired
    private ReservacionService reservacionService;
    @Autowired
    private HabitacionRepository habitacionRepository;


    @GetMapping("/lista")
    public List<Reservacion> listar() {
        return reservacionService.obtenerTodas();
    }

    @PostMapping("/nueva")
    public String crear(@Valid @RequestBody Reservacion reservacion) {

        // Primero verificamos si la habitación existe de forma rápida para dar el mensaje correcto
        boolean existeHabitacion = habitacionRepository.existsById(reservacion.getHabitacion().getNumero());
        if (!existeHabitacion) {
            return "Error: La habitación especificada no existe.";
        }

        Reservacion nuevaReserva = reservacionService.guardarReservacion(reservacion);
        if (nuevaReserva != null) {
            return "Reservación creada con éxito para el cliente: " + reservacion.getCliente().getNombre();
        } else {
            return "Error: La habitación ya se encuentra ocupada en las fechas seleccionadas.";
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarReservacion(@PathVariable Long id){
        boolean eliminado = reservacionService.eliminarReservacion(id);
        if(eliminado){
            return "Reservacion con ID #" + id + " eliminada cn éxito!";
        }
        return "Error: La reservación con ID #" + id + " no existe.";
    }
}