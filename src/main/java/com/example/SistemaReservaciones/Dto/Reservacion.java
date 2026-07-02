package com.example.SistemaReservaciones.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="reservaciones")
public class Reservacion {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

       // @NotBlank(message = "El nombre del cliente no puede estar vacío.")
       // private String nombreCliente;

        @NotNull(message = "La fecha de ingreso es obligatoria")
        @FutureOrPresent(message = "La fecha de entrada no puede ser una fecha pasada.")
        private LocalDate fechaEntrada;

        @NotNull(message = "La fecha de salida es obligatoria")
        private LocalDate fechaSalida;

        private Double precioTotal;

        @ManyToOne
        @JoinColumn(name = "habitacion_numero", nullable = false)
        @NotNull(message = "La habitación asignada es obligatoria")
        private Habitacion habitacion;

        @ManyToOne
        @JoinColumn(name = "cliente_id")
        @NotNull(message = "El cliente asociado es obligatorio.")
        private Cliente cliente;


        public Reservacion(){

        }

    public Reservacion(Long id, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = id;
      //  this.nombreCliente = nombreCliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getNombreCliente() {
//        return nombreCliente;
//    }

//    public void setNombreCliente(String nombreCliente) {
//        this.nombreCliente = nombreCliente;
//    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
