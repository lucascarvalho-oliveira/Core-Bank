package application.controller;

import model.CPF;
import model.Pessoa;
import model.Pix;
import model.enums.TipoConta;
import model.enums.TipoPessoa;
import model.enums.TipoPix;
import repository.PessoaRepository;
import service.ContaService;
import service.PessoaService;
import service.PixService;

import java.util.Scanner;

public class CriarContaController {
    private PessoaService servicePessoa;
    private PessoaRepository repositoryPessoa;
    private ContaService serviceConta;
    private PixService servicePix;
    private CPF cpf;

    public CriarContaController(PessoaService servicePessoa, PessoaRepository repositoryPessoa, ContaService serviceConta, PixService servicePix, CPF cpf){
        this.servicePessoa = servicePessoa;
        this.repositoryPessoa = repositoryPessoa;
        this.serviceConta = serviceConta;
        this.servicePix = servicePix;
        this.cpf = cpf;
    }

    public void criarConta(Scanner sc){
        do{
            System.out.println("1 - Cadastrar cooperado.");
            System.out.println("2 - Criar conta.");
            System.out.println("3 - voltar ao menu anterior.");
            System.out.println("Escolha uma das opções.");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("\nSeu nome completo:");
                    String nome = sc.nextLine();
                    System.out.println("\n=== Tipo de Pessoa ===");
                    System.out.println("1 - Pessoa Física");
                    System.out.println("2 - Pessoa Jurídica");
                    int menuPessoa = sc.nextInt();
                    sc.nextLine();

                    TipoPessoa tipoPessoa = null;
                    if (menuPessoa == 1) {
                        tipoPessoa = TipoPessoa.PESSOA_FISiCA;
                        System.out.println("\nInforme o seu CPF:");
                    } else {
                        tipoPessoa = TipoPessoa.PESSOA_JURIDICA;
                        System.out.println("\nInforme o seu CNPJ:");
                    }
                    String documento = sc.nextLine();

                    try {
                        System.out.println("Crie um senha para a sua conta. No mínimo um simbolo e uma letra maiúscula:");
                        String senha = sc.nextLine();

                        Pessoa pessoa = new Pessoa(nome, tipoPessoa, documento, senha);
                        servicePessoa.salvarPessoa(pessoa);
                        break;

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Informe o documento.");
                    String doc = sc.nextLine();

                    Pessoa pessoa = repositoryPessoa.buscarPessoa(doc);

                    if(pessoa == null){
                        System.out.println("\nPessoa nao encontrada\n");
                        break;
                    }

                    System.out.println("\nQual o tipo da conta que voce deseja criar:\n");
                    System.out.println("1 - Conta Corrente:");
                    System.out.println("Ideal para movimentações do dia a dia.\n" +
                            "Possui limite de crédito para uso emergencial.\n");

                    System.out.println("2 - Conta Poupança:");
                    System.out.println("Ideal para guardar dinheiro.\n" +
                            "Possui rendimento sobre o saldo disponível.\n");

                    System.out.println("Coloque o numero da conta desejada:");

                    int menuTipoConta = sc.nextInt();

                    TipoConta tipoConta = null;
                    if(menuTipoConta == 1){
                        tipoConta = TipoConta.CONTA_CORRENTE;

                    }else if(menuTipoConta == 2){
                        tipoConta = TipoConta.CONTA_POUPANCA;

                    }else{
                        System.out.println("Opção invalida!");
                        break;
                    }

                    System.out.println("\nCrie seu pix:");
                    System.out.println("escolha a sua chave pix");
                    System.out.println("1 - CPF");
                    System.out.println("2 - Telefone:");
                    System.out.println("3 - Email");
                    int menuPix = sc.nextInt();sc.nextLine();

                    TipoPix tipoPix = null;
                    String chave = "";

                    switch (menuPix){
                        case 1:
                            tipoPix = TipoPix.CPF;

                            System.out.println("\nInforme o seu Cpf:");
                            chave = sc.nextLine();
                            cpf.confirmaCpf(chave);
                            break;
                        case 2:
                            tipoPix = TipoPix.TELEFONE;

                            System.out.println("\nInforme o seu telefone:");
                            chave = sc.nextLine();
                            break;
                        case 3:
                            tipoPix = TipoPix.EMAIL;

                            System.out.println("\nInforme o seu email:");
                            chave = sc.nextLine();
                            break;
                        default:
                            System.out.println("\nOpção inválida!\n");
                            break;
                    }

                    if(tipoPix != null && !chave.isBlank()) {
                        Pix pix = new Pix(tipoPix, chave, serviceConta.criarConta(tipoConta, pessoa));
                        servicePix.savePix(pix);

                    }else{
                        System.out.println("ERRO! pix invalido");
                        break;
                    }
                    break;

                case 3:
                    System.out.println();
                    break;

                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        }while(true);
    }
}
