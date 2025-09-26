package br.org.umc.spring.projeto.model;

public class Paciente extends Pessoa {
    private final boolean primeiraConsulta;

    public Paciente(Long id, String nome, boolean primeiraConsulta) {
        super(id, nome);
        this.primeiraConsulta = primeiraConsulta;
    }

    public boolean isPrimeiraConsulta() {
        return primeiraConsulta;
    }

    @Override
    public String toString() {
        return getNome() + " (" + getIdade() + " anos" + (primeiraConsulta ? ", 1ª consulta" : "") + ")";
    }
}
