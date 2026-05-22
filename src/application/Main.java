package application;

import application.controller.ContaController;
import application.controller.CriarContaController;
import application.controller.LoginController;
import model.Pessoa;
import model.Senha;
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

        boolean sair = false;
        do {
            System.out.println("==================== Core Bank ====================");
            System.out.println("1 - Criar conta:");
            System.out.println("2 - Fazer login:");
            System.out.println("3 - sair do sistema:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    PessoaService servicePessoa = new PessoaService(repositoryPessoa, senha);
                    CriarContaController controllerCriarConta = new CriarContaController(servicePessoa);

                    controllerCriarConta.criarConta(sc);
                    break;
                case 2:
                    ContaRepository repositoryConta = new ContaRepository();
                    PixRepository repositoryPix = new PixRepository();
                    TransacaoRepository repositoryTransacao = new TransacaoRepository();

                    ContaService serviceConta = new ContaService(repositoryConta, repositoryPix);
                    LoginController controllerLogin = new LoginController(repositoryPessoa, senha);

                    Pessoa pessoa = controllerLogin.loginPessoa(sc);

                    ContaController controllerConta = new ContaController(serviceConta, repositoryConta, repositoryTransacao);

                    controllerConta.conta(sc, pessoa.getId_pessoa());

            }


        }while(!sair);

        sc.close();
    }
}
