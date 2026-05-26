package application;

import application.controller.ContaController;
import application.controller.CriarContaController;
import application.controller.LoginController;
import model.*;
import repository.ContaRepository;
import repository.PessoaRepository;
import repository.PixRepository;
import repository.TransacaoRepository;
import service.ContaService;
import service.PessoaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        PessoaRepository repositoryPessoa = new PessoaRepository();
        Senha senha = new Senha();
        CPF cpf = new CPF();

        boolean sair = false;
        do {
            System.out.println("==================== Core Bank ====================");
            System.out.println("1 - Criar conta:");
            System.out.println("2 - Fazer login:");
            System.out.println("3 - sair do sistema:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    CNPJ cnpj = new CNPJ();
                    PessoaService servicePessoa = new PessoaService(repositoryPessoa, senha, cpf, cnpj);
                    CriarContaController controllerCriarConta = new CriarContaController(servicePessoa);

                    controllerCriarConta.criarConta(sc);
                    break;

                case 2:
                    ContaRepository repositoryConta = new ContaRepository();
                    PixRepository repositoryPix = new PixRepository();
                    TransacaoRepository repositoryTransacao = new TransacaoRepository();

                    LoginController controllerLogin = new LoginController(repositoryPessoa, senha);

                    Pessoa pessoa = controllerLogin.loginPessoa(sc);

                    ContaService serviceConta = new ContaService(repositoryConta, repositoryPix, pessoa);
                    ContaController controllerConta = new ContaController(serviceConta, repositoryConta, repositoryTransacao, repositoryPix, cpf);

                    controllerConta.conta(sc, pessoa.getId_pessoa());
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
