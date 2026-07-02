package com.example.SistemaReservaciones.Exceptions;

public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String mensaje){
         super(mensaje);
    }
}
