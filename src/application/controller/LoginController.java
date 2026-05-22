package application.controller;

import model.Pessoa;
import model.Senha;
import repository.PessoaRepository;

import java.util.Scanner;

public class LoginController {
    private PessoaRepository repositoryPessoa;
    private Senha senha;

    public LoginController(PessoaRepository repositoryPessoa, Senha senha){
        this.repositoryPessoa = repositoryPessoa;
        this.senha = senha;
    }

    public Pessoa loginPessoa(Scanner sc){
        Pessoa pessoa = null;
        while (true) {
            System.out.println("Informe o seu documento:");
            String documento = sc.nextLine();

            pessoa = repositoryPessoa.buscarPessoa(documento);

            if (pessoa == null) {
                System.out.println("Usuário não encontrado! Tente novamente.");
                continue;
            }

            System.out.println("Informe a senha da conta:");
            String senhaConta = sc.nextLine();

            if(senha.validarSenha(pessoa, senhaConta)){
                System.out.println("Login feito com sucesso!");
                break;
            }else{
                System.out.println("Senha incorreta!");
            }
        }
        return pessoa;
    }
}
