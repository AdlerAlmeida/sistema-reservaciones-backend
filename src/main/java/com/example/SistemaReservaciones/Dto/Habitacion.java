package com.example.SistemaReservaciones.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @Min(value=1,message = "El número de habitación debe ser mayor a 0")
    private Integer numero;

    @NotBlank(message = "El tipo de habitación no puede estar vacío.")
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    public Habitacion() {
    }

    public Habitacion(Integer numero, String tipo, Double precio) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
