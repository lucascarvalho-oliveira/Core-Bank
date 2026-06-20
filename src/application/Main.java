package application;

import application.controller.*;
import model.*;
import repository.*;
import service.*;

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
        TransacaoService serviceTransacao = new TransacaoService(repositoryTransacao);

        boolean sair = false;
        do {
            System.out.println("==================== Core Bank ====================");
            System.out.println("1 - Criar conta:");
            System.out.println("2 - Gestão do Cooperado:");
            System.out.println("3 - Fazer login:");
            System.out.println("4 - Investimento:");
            System.out.println("5 - Transação:");
            System.out.println("6 - sair do sistema:");
            int menu = sc.nextInt();sc.nextLine();

            Pessoa pessoaLogada = null;

            switch (menu){
                case 1:
                    CriarContaController controllerCriarConta = new CriarContaController(servicePessoa, repositoryPessoa, serviceConta, servicePix, cpf);
                    controllerCriarConta.criarConta(sc);
                    break;

                case 2:
                    CooperadoController controllerCooperado = new CooperadoController(repositoryPessoa, senha);
                    controllerCooperado.cooperado(sc);
                    break;

                case 3:
                    LoginController controllerLogin = new LoginController(repositoryPessoa, senha);
                    pessoaLogada  = controllerLogin.loginPessoa(sc);

                    ContaPixController controllerConta = new ContaPixController(serviceConta, repositoryConta, repositoryPix, serviceTransacao, cpf);
                    controllerConta.conta(sc, pessoaLogada.getIdPessoa());
                    break;

                case 4:
                    if(pessoaLogada == null){
                        System.out.println("Faça o login primero.");
                        break;
                    }

                    InvestimentoController controllerInvest = new InvestimentoController(serviceInveste, repositoryInveste, serviceConta, repositoryConta, serviceTransacao);
                    controllerInvest.investimento(sc, pessoaLogada.getIdPessoa());
                    break;

                case 5:
                    if(pessoaLogada == null){
                        System.out.println("Faça o login primero.");
                        break;
                    }



                case 6:
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
