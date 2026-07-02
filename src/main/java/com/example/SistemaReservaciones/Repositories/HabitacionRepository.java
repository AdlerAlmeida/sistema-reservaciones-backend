package com.example.SistemaReservaciones.Repositories;

// IMPORTANTE: Asegúrate de que apunte a tu Dto o Modelo
import com.example.SistemaReservaciones.Dto.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Integer>{

    @Query("SELECT h FROM Habitacion h WHERE h.id NOT IN (" +
            "SELECT r.habitacion.id FROM Reservacion r WHERE " +
            "r.fechaEntrada < :fechaSalidaBusqueda AND r.fechaSalida > :fechaEntradaBusqueda)")
    List<Habitacion> encontrarHabitacionesLibres(
            @Param("fechaEntradaBusqueda") LocalDate fechaEntrada,
            @Param("fechaSalidaBusqueda") LocalDate fechaSalida
    );
}