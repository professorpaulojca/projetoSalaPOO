package br.org.umc.spring.projeto.model;

import java.util.ArrayList;

public class Empresa {

    private ArrayList<Pessoa> socios;

    public ArrayList<Pessoa> getSocios() {
        return socios;
    }

    public void setSocios(ArrayList<Pessoa> socios) {
        this.socios = socios;
    }

    public Empresa(ArrayList<Pessoa> socios) {
        this.socios = socios;
    }
}
