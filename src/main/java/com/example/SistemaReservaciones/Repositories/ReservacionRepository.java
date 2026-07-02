package com.example.SistemaReservaciones.Repositories;

import com.example.SistemaReservaciones.Dto.Habitacion;
import com.example.SistemaReservaciones.Dto.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    boolean existsByHabitacionAndFechaEntradaLessThanEqualAndFechaSalidaGreaterThanEqual(
            Habitacion habitacion,
            LocalDate fechaSalidaSolicitada,
            LocalDate fechaEntradaSolicitada
    );
}