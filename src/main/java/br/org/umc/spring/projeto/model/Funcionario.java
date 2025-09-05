package br.org.umc.spring.projeto.model;

class Funcionario extends Pessoa {

    private String matricula;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Funcionario(Long id, String nome, String matricula) {
        super(id, nome);
        this.matricula = matricula;
    }
}
