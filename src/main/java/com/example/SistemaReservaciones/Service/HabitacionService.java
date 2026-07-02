package com.example.SistemaReservaciones.Service;

import com.example.SistemaReservaciones.Dto.Habitacion;
import com.example.SistemaReservaciones.Exceptions.InvalidPriceException;
import com.example.SistemaReservaciones.Repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
// 1. Le avisamos a Spring que este es nuestro Servicio (Capa de Negocio)
@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Habitacion> obtenerTodas(){
    return habitacionRepository.findAll();
    }

    public void guardarHabitacion(Habitacion habitacion){
        if (habitacion.getPrecio() <= 0){
            throw new InvalidPriceException("El precio de la habitación debe ser mayor 0");
        }
        habitacionRepository.save(habitacion);
    }

    public Habitacion buscarPorNumero(int numero){
        return habitacionRepository.findById(numero).orElse(null);
    }

    public boolean eliminarHabitacioan(int id){
        if(habitacionRepository.existsById(id)){
            habitacionRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public Habitacion actualizarHabitacion (int numero, Habitacion habitacionActualizada){
        return habitacionRepository.findById(numero).map(habitacionExistente -> {
            // Le pasamos los nuevos datos del JSON al objeto que ya está en la BD
            habitacionExistente.setTipo(habitacionActualizada.getTipo());
            habitacionExistente.setPrecio(habitacionActualizada.getPrecio());
            // Guardamos los cambios. .save() hace un UPDATE si el ID ya existe
            return habitacionRepository.save(habitacionExistente);
        }).orElse(null);
    }

    public List<Habitacion> obtenerHabitacionDisponibles(LocalDate fechaEntrada, LocalDate fechaSalida){
       return habitacionRepository.encontrarHabitacionesLibres(fechaEntrada,fechaSalida);
    }
}