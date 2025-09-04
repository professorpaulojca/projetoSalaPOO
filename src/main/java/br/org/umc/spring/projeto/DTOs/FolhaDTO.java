package br.org.umc.spring.projeto.DTOs;

public class FolhaDTO {
    public String nomeFuncionario;
    public double salario;

    public FolhaDTO(String nomeFuncionario, double salario) {
        this.nomeFuncionario = nomeFuncionario;
        this.salario = salario;
    }
}
