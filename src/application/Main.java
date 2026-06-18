package application;

import application.controller.ContaController;
import application.controller.CriarContaController;
import application.controller.LoginController;
import model.*;
import repository.*;
import service.ContaService;
import service.InvestimentoService;
import service.PessoaService;
import service.PixService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        // Pessoa.
        PessoaRepository repositoryPessoa = new PessoaRepository();
        Senha senha = new Senha();
        CPF cpf = new CPF();
        CNPJ cnpj = new CNPJ();
        PessoaService servicePessoa = new PessoaService(repositoryPessoa, senha, cpf, cnpj);

        // Pix
        PixRepository repositoryPix = new PixRepository();
        PixService servicePix = new PixService(repositoryPix);

        // Conta.
        ContaRepository repositoryConta = new ContaRepository();
        ContaService serviceConta = new ContaService(repositoryConta, repositoryPix);

        // Investimento
        InvestimentoRepository repositoryInveste = new InvestimentoRepository();
        InvestimentoService serviceInveste = new InvestimentoService(repositoryInveste, serviceConta);

        // Transação.
        TransacaoRepository repositoryTransacao = new TransacaoRepository();

        boolean sair = false;
        do {
            System.out.println("==================== Core Bank ====================");
            System.out.println("1 - Criar conta:");
            System.out.println("2 - Fazer login:");
            System.out.println("3 - sair do sistema:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    CriarContaController controllerCriarConta = new CriarContaController(servicePessoa, repositoryPessoa, serviceConta, servicePix, cpf);
                    controllerCriarConta.criarConta(sc);
                    break;

                case 2:
                    LoginController controllerLogin = new LoginController(repositoryPessoa, senha);
                    Pessoa pessoa = controllerLogin.loginPessoa(sc);

                    ContaController controllerConta = new ContaController(serviceConta, repositoryConta, serviceInveste, repositoryPix, repositoryTransacao);
                    controllerConta.conta(sc, pessoa.getIdPessoa());
                    break;

                case 3:
                    System.out.println("\nPrograma finalizado!");
                    sair = true;
                    break;

                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        }while(!sair);

        sc.close();
    }
}
