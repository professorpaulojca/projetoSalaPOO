package br.org.umc.spring.projeto.model;

import java.util.ArrayList;
import java.util.Date;

public class Paciente extends Pessoa {
    private Date dataCadastro;
    private ArrayList<PlanoSaude> planoSaudes;

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public ArrayList<PlanoSaude> getPlanoSaudes() {
        return planoSaudes;
    }

    public void setPlanoSaudes(ArrayList<PlanoSaude> planoSaudes) {
        this.planoSaudes = planoSaudes;
    }

    public Paciente(Date dataCadastro, ArrayList<PlanoSaude> planoSaudes) {
        this.dataCadastro = dataCadastro;
        this.planoSaudes = planoSaudes;
    }

    public Paciente(int id, String nome, Date dataCadastro, ArrayList<PlanoSaude> planoSaudes) {
        super(id, nome);
        this.dataCadastro = dataCadastro;
        this.planoSaudes = planoSaudes;
    }
}
