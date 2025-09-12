package br.org.umc.spring.projeto.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String message) { super(message); }
    public EstoqueInsuficienteException(String message, Throwable cause) { super(message, cause); }
}
