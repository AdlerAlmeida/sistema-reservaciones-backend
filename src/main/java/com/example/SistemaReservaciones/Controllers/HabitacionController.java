package com.example.SistemaReservaciones.Controllers;

import com.example.SistemaReservaciones.Dto.Habitacion;
import com.example.SistemaReservaciones.Service.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HabitacionController {
    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("/lista")
    public List<Habitacion> leerHabitaciones(){
        return habitacionService.obtenerTodas();
    }

    @PostMapping("/nueva")
    public ResponseEntity<?> crearHabitacion(@Valid @RequestBody Habitacion habitacion){
        habitacionService.guardarHabitacion(habitacion);
        return ResponseEntity.ok("habitación creada con éxito!");
    }

    @GetMapping("/buscar/{numero}")
    public Habitacion buscarPorNumero(@PathVariable int numero) {
        return habitacionService.buscarPorNumero(numero);
    }

    @DeleteMapping("/eliminar/{numero}")
    public String eliminarHabitacion(@PathVariable int numero){
        boolean eliminado = habitacionService.eliminarHabitacioan(numero);
        if(eliminado){
            return "Habitacion #" + numero + " eliminada cn éxito!";
        }
        return "Error: La habitacion #" + numero + " no existe.";
    }

    @PatchMapping("/actualizar/{numero}")
    public String actualizarHabitacion(@PathVariable int numero, @Valid @RequestBody Habitacion habitacion){
        Habitacion actualizada = habitacionService.actualizarHabitacion(numero,habitacion);
        if(actualizada != null){
            return "Habitacion #" + numero + " actualizada con exito.";
        }

        return "Error: No se pudo actualizar porque la habitacion #" + numero + " no existe.";
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Habitacion>> obtenerDisponibles(
            @RequestParam("entrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fechaEntrada,
            @RequestParam("salida")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fechaSalida
            ){

        List<Habitacion> libres = habitacionService.obtenerHabitacionDisponibles(fechaEntrada,fechaSalida);
        return ResponseEntity.ok(libres);
    }
}