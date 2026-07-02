package com.example.SistemaReservaciones.Repositories;

import com.example.SistemaReservaciones.Dto.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
