package com.example.SistemaReservaciones.Repositories;

import com.example.SistemaReservaciones.Dto.Habitacion;
import com.example.SistemaReservaciones.Dto.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    @Query("SELECT COUNT(r) > 0 FROM Reservacion r WHERE r.habitacion = :habitacion " +
            "AND (:fechaEntrada < r.fechaSalida AND :fechaSalida > r.fechaEntrada)")
    boolean verificarTraslape(
            @Param("habitacion") Habitacion habitacion,
            @Param("fechaEntrada") LocalDate fechaEntrada,
            @Param("fechaSalida") LocalDate fechaSalida
    );

}