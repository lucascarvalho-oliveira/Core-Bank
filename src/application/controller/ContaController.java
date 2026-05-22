package application.controller;

import model.CPF;
import model.Conta;
import model.Pessoa;
import model.Pix;
import model.enums.TipoConta;
import model.enums.TipoPix;
import repository.ContaRepository;
import repository.TransacaoRepository;
import service.ContaService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class ContaController {
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private TransacaoRepository repositoryTransacao;

    public ContaController(ContaService serviceConta, ContaRepository repositoryConta, TransacaoRepository repositoryTransacao){
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.repositoryTransacao = repositoryTransacao;
    }

    public void conta(Scanner sc, int id_pessoa){
        Conta conta = repositoryConta.buscarContaIdPessoa(id_pessoa);

        boolean sair = false;
        do{
            System.out.println("1 - Criar nova conta:");
            System.out.println("2 - Consultar Saldo e ver histórico:");
            System.out.println("3 - Transações Bancarias:");
            System.out.println("4 - sair:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    System.out.println("Qual o tipo da conta que voce deseja criar:");
                    System.out.println("1 - Conta Corrente:");
                    System.out.println("Ideal para movimentações do dia a dia.\n " +
                            "Possui limite de crédito para uso emergencial.\n");

                    System.out.println("2 - Conta Poupança:");
                    System.out.println("Ideal para guardar dinheiro.\n" +
                            "Possui rendimento sobre o saldo disponível.\n");

                    int menuTipoConta = sc.nextInt();

                    TipoConta tipoConta = null;
                    if(menuTipoConta == 1){
                        tipoConta = TipoConta.CONTA_CORRENTE;
                    }else{
                        tipoConta = TipoConta.CONTA_POUPANCA;
                    }

                    System.out.println("Crie seu pix:");
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

                            System.out.println("Informe o seu Cpf:");
                            chave = sc.nextLine();
                            CPF cpf = new CPF(chave);
                            break;
                        case 2:
                            tipoPix = TipoPix.TELEFONE;

                            System.out.println("Informe o seu telefone:");
                            chave = sc.nextLine();
                            break;
                        case 3:
                            tipoPix = TipoPix.EMAIL;

                            System.out.println("Informe o seu email:");
                            chave = sc.nextLine();
                            break;
                        default:
                            System.out.println("\nOpção inválida!\n");
                            break;
                    }

                    Pix pix = new Pix(tipoPix, chave, serviceConta.criarConta(tipoConta));
                    break;

                case 2:
                    System.out.println("saldo em conta: R$ " + conta.getSaldo());
                    System.out.println();

                    repositoryTransacao.extrato(conta.getId_conta());
                    break;

                case 3:
                    System.out.println("Qual a transação bancaria você deseja fazer:");
                    System.out.println("Saldo em conta: R$ " + conta.getSaldo());

                    System.out.println("\n1 - Depositar:");
                    System.out.println("2 - Sacar:");
                    System.out.println("3 - Transferência vai Pix:");
                    int menuTransacao = sc.nextInt();sc.nextLine();

                    try {
                        switch (menuTransacao) {
                            case 1:
                                System.out.println("Informe o valor do deposito:");
                                double valorDeposito = sc.nextDouble();sc.nextLine();

                                serviceConta.depositar(conta, valorDeposito);
                                break;

                            case 2:
                                System.out.println("Informe o valor do saque:");
                                double valorSaque = sc.nextDouble();sc.nextLine();

                                if(conta.getTipoConta() == TipoConta.CONTA_CORRENTE && valorSaque > conta.getSaldo()){
                                    System.out.println("Deseja utilizar o limite em conta: limite R$ " + conta.getLimite());
                                    System.out.println("1 - Sim | 2 - Não");
                                    int menuLimite = sc.nextInt(); sc.nextLine();

                                    if(menuLimite == 1){
                                        serviceConta.sacar(conta, valorSaque, true);
                                    }else{
                                        System.out.println("Saldo insuficiente para essa transação.");
                                    }
                                }
                                serviceConta.sacar(conta, valorSaque, false);
                                break;

                            case 3:
                        }
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
            }

        }while(!sair);
    }
}
