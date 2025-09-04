package br.org.umc.spring.projeto.services;

import br.org.umc.spring.projeto.DTOs.FolhaDTO;
import br.org.umc.spring.projeto.enums.TipoContato;
import br.org.umc.spring.projeto.interfaces.IBonus;
import br.org.umc.spring.projeto.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class FuncionarioService {


    public Funcionario cadastrarFuncionario(Funcionario func) {

        return func;
    }

}
