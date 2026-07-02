package com.example.SistemaReservaciones.Repositories;

// IMPORTANTE: Asegúrate de que apunte a tu Dto o Modelo
import com.example.SistemaReservaciones.Dto.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Integer>{

}