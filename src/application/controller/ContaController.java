package application.controller;

import model.*;
import model.enums.TipoConta;
import model.enums.TipoPix;
import model.enums.TipoTransacao;
import repository.ContaRepository;
import repository.TransacaoRepository;
import service.ContaService;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ContaController {
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private TransacaoRepository repositoryTransacao;
    private CPF cpf;

    public ContaController(ContaService serviceConta, ContaRepository repositoryConta, TransacaoRepository repositoryTransacao, CPF cpf){
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.repositoryTransacao = repositoryTransacao;
        this.cpf = cpf;
    }

    public void conta(Scanner sc, int id_pessoa){
        boolean sair = false;
        do{
            Conta conta = repositoryConta.buscarContaIdPessoa(id_pessoa);

            System.out.println("\n1 - Criar nova conta:");
            System.out.println("2 - Ver histórico:");
            System.out.println("3 - Transações Bancarias:");
            System.out.println("4 - Voltar:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    System.out.println("\nQual o tipo da conta que voce deseja criar:\n");
                    System.out.println("1 - Conta Corrente:");
                    System.out.println("Ideal para movimentações do dia a dia.\n " +
                            "Possui limite de crédito para uso emergencial.\n");

                    System.out.println("2 - Conta Poupança:");
                    System.out.println("Ideal para guardar dinheiro.\n" +
                            "Possui rendimento sobre o saldo disponível.\n");

                    System.out.println("Coloque o numero da conta desejada:");

                    int menuTipoConta = sc.nextInt();

                    TipoConta tipoConta = null;
                    if(menuTipoConta == 1){
                        tipoConta = TipoConta.CONTA_CORRENTE;
                    }else{
                        tipoConta = TipoConta.CONTA_POUPANCA;
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

                    Pix pix = new Pix(tipoPix, chave, serviceConta.criarConta(tipoConta));
                    conta = repositoryConta.buscarContaIdPessoa(id_pessoa);

                    break;

                case 2:
                    if(conta == null){
                        System.out.println("Você precisa criar um conta primeiro!");
                    }

                    repositoryTransacao.extrato(conta.getId_conta());
                    break;

                case 3:
                    if(conta == null){
                        System.out.println("Você precisa criar um conta primeiro!");
                    }

                    System.out.println("\nQual a transação bancaria você deseja fazer:");
                    System.out.println("Saldo em conta: R$ " + conta.getSaldo());

                    System.out.println("\n1 - Depositar:");
                    System.out.println("2 - Sacar:");
                    System.out.println("3 - Transferência via Pix:");
                    int menuTransacao = sc.nextInt();sc.nextLine();

                    try {
                        switch (menuTransacao) {
                            case 1:
                                System.out.println("\nInforme o valor do deposito:");
                                double valorDeposito = sc.nextDouble();sc.nextLine();

                                serviceConta.depositar(conta, valorDeposito);

                                Transacao transacaoDepositar = new Transacao(LocalDateTime.now(), valorDeposito, conta.getSaldo(), TipoTransacao.DEPOSITO, conta);
                                repositoryTransacao.salvarTransacao(transacaoDepositar);
                                break;

                            case 2:
                                System.out.println("\nInforme o valor do saque:");
                                double valorSaque = sc.nextDouble();sc.nextLine();

                                if(conta.getTipoConta() == TipoConta.CONTA_CORRENTE && valorSaque > conta.getSaldo()){
                                    System.out.println("\nSaldo insuficiente para realizar esta transação. Deseja utilizar o limite de crédito? Ao utilizar o limite, será aplicada uma taxa adicional de 20%.");
                                    System.out.println("Limite de crédito: R$ " + conta.getLimite());
                                    System.out.println("1 - Sim | 2 - Não");
                                    int menuLimite = sc.nextInt(); sc.nextLine();

                                    if(menuLimite == 1){
                                        serviceConta.sacar(conta, valorSaque, true);

                                        Transacao transacaoSacar = new Transacao(LocalDateTime.now(), valorSaque, conta.getSaldo(), TipoTransacao.SAQUE, conta);
                                        repositoryTransacao.salvarTransacao(transacaoSacar);
                                    }else{
                                        System.out.println("\nSaldo insuficiente para essa transação.\n");
                                    }
                                }
                                if(valorSaque <= conta.getSaldo()) {
                                    serviceConta.sacar(conta, valorSaque, false);

                                    Transacao transacaoSacar = new Transacao(LocalDateTime.now(), valorSaque, conta.getSaldo(), TipoTransacao.SAQUE, conta);
                                    repositoryTransacao.salvarTransacao(transacaoSacar);
                                }
                                break;

                            case 3:
                                System.out.println("\nInforme o valor da transferência:");
                                double valorTransferencia = sc.nextDouble();sc.nextLine();
                                System.out.println("Informe a chave pix:");
                                String chavePix = sc.nextLine();

                                if(conta.getTipoConta() == TipoConta.CONTA_CORRENTE && valorTransferencia > conta.getSaldo()) {
                                    System.out.println("\nSaldo insuficiente para realizar esta transação. Deseja utilizar o limite de crédito? Ao utilizar o limite, será aplicada uma taxa adicional de 20%.");
                                    System.out.println("Limite de crédito: R$ " + conta.getLimite());
                                    System.out.println("1 - Sim | 2 - Não");
                                    int menuLimite = sc.nextInt();sc.nextLine();

                                    if (menuLimite == 1) {
                                        serviceConta.transferir(conta, valorTransferencia, true, chavePix);

                                        Transacao transacaoTransferencia = new Transacao(LocalDateTime.now(), valorTransferencia, conta.getSaldo(), TipoTransacao.MOVIMENTACAO, conta);
                                        repositoryTransacao.salvarTransacao(transacaoTransferencia);
                                    } else {
                                        System.out.println("\nSaldo insuficiente para essa transação.\n");
                                    }
                                }
                                if(valorTransferencia <= conta.getSaldo()) {
                                    serviceConta.transferir(conta, valorTransferencia, false, chavePix);

                                    Transacao transacaoTransferencia = new Transacao(LocalDateTime.now(), valorTransferencia, conta.getSaldo(), TipoTransacao.MOVIMENTACAO, conta);
                                    repositoryTransacao.salvarTransacao(transacaoTransferencia);
                                }
                                break;

                            case 4:
                                sair = true;
                                break;

                            default:
                                System.out.println("\nOpção inválida!\n");
                                break;
                        }
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
            }

        }while(!sair);
    }
}
