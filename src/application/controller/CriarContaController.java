package application.controller;

import model.Pessoa;
import model.enums.TipoPessoa;
import service.PessoaService;

import java.util.Scanner;

public class CriarContaController {
    private PessoaService servicePessoa;

    public CriarContaController(PessoaService servicePessoa){
        this.servicePessoa = servicePessoa;
    }

    public void criarConta(Scanner sc){
        do{
            System.out.println("Seu nome completo:");
            String nome = sc.nextLine();
            System.out.println("=== Tipo de Pessoa ===");
            System.out.println("1 - Pessoa Física");
            System.out.println("2 - Pessoa Jurídica");
            int menu = sc.nextInt();sc.nextLine();

            TipoPessoa tipoPessoa = null;
            if(menu == 1){
                tipoPessoa = TipoPessoa.PESSOA_FISiCA;
                System.out.println("Informe o seu CPF:");
            }else{
                tipoPessoa = TipoPessoa.PESSOA_JURIDICA;
                System.out.println("Informe o seu CNPJ:");
            }

            String documento = sc.nextLine();
            System.out.println("Crie um senha para a sua conta. No mínimo um simbolo e uma letra maiúscula:");
            String senha = sc.nextLine();

            Pessoa pessoa = new Pessoa(nome, tipoPessoa, documento, senha);
            servicePessoa.salvarPessoa(pessoa);
        }while(true);
    }
}
