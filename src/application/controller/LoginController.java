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
            System.out.println("\nInforme o seu documento:");
            String documento = sc.nextLine();

            pessoa = repositoryPessoa.buscarPessoa(documento);

            if (pessoa == null) {
                System.out.println("\nUsuário não encontrado! Tente novamente.");
                continue;
            }

            System.out.println("Informe a senha da conta:");
            String senhaConta = sc.nextLine();

            if(senha.validarSenha(pessoa, senhaConta)){
                System.out.println("\nLogin feito com sucesso!");
                break;
            }else{
                System.out.println("\nSenha incorreta!");
            }
        }
        return pessoa;
    }
}
