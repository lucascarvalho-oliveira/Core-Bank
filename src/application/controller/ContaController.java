package application.controller;

import model.*;
import model.enums.TipoConta;
import model.enums.TipoPix;
import model.enums.TipoTransacao;
import repository.ContaRepository;
import repository.PixRepository;
import repository.TransacaoRepository;
import service.ContaService;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ContaController {
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private TransacaoRepository repositoryTransacao;
    private PixRepository repositoryPix;
    private CPF cpf;

    public ContaController(ContaService serviceConta, ContaRepository repositoryConta, TransacaoRepository repositoryTransacao, PixRepository repositoryPix, CPF cpf){
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.repositoryTransacao = repositoryTransacao;
        this.repositoryPix = repositoryPix;
        this.cpf = cpf;
    }

    public void conta(Scanner sc, int id_pessoa){
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
                        Pix pix = new Pix(tipoPix, chave, serviceConta.criarConta(tipoConta));
                        repositoryPix.salvarPix(pix);
                        conta = repositoryConta.buscarContaIdPessoa(id_pessoa);
                    }else{
                        System.out.println("ERRO! pix invalido");
                        break;
                    }
                    break;

                case 2:
                    if(conta == null){
                        System.out.println("Você precisa criar um conta primeiro!");
                        break;
                    }

                    repositoryTransacao.extrato(conta.getId_conta());
                    break;

                case 3:
                    if(conta == null){
                        System.out.println("Você precisa criar um conta primeiro!");
                        break;
                    }

                    boolean sairMenuConta = false;
                    do {
                        System.out.println("\nQual a transação bancaria você deseja fazer:");
                        System.out.println("Saldo em conta: R$ " + conta.getSaldo());

                        System.out.println("\n1 - Depositar:");
                        System.out.println("2 - Sacar:");
                        System.out.println("3 - Transferência via Pix:");
                        System.out.println("4 - voltar:");
                        int menuTransacao = sc.nextInt();
                        sc.nextLine();

                        switch (menuTransacao) {
                            case 1:
                                System.out.println("\nInforme o valor do deposito:");
                                double valorDeposito = sc.nextDouble();sc.nextLine();

                                try {
                                    serviceConta.depositar(conta, valorDeposito);

                                    Transacao transacaoDepositar = new Transacao(LocalDateTime.now(), valorDeposito, conta.getSaldo(), TipoTransacao.DEPOSITO, conta);

                                    repositoryTransacao.salvarTransacao(transacaoDepositar);

                                }catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("\nSaldo atualizado!");
                                break;

                            case 2:
                                if(conta.getTipoConta() == TipoConta.CONTA_CORRENTE) {
                                    System.out.println("\nvalor do cheque especial disponível: R$ " + conta.getLimite());
                                }
                                System.out.println("\nInforme o valor do saque:");
                                double valorSaque = sc.nextDouble();
                                sc.nextLine();

                                try{
                                    serviceConta.sacar(conta, valorSaque);

                                    Transacao transacaoSacar = new Transacao(LocalDateTime.now(), valorSaque, conta.getSaldo(), TipoTransacao.SAQUE, conta);
                                    repositoryTransacao.salvarTransacao(transacaoSacar);

                                }catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("\nSaldo atualizado!");
                                break;

                            case 3:
                                System.out.println("\nInforme o valor da transferência:");
                                double valorTransferencia = sc.nextDouble();
                                sc.nextLine();
                                System.out.println("Informe a chave pix:");
                                String chavePix = sc.nextLine();

                                try{
                                    serviceConta.transferir(conta, valorTransferencia, chavePix);

                                    Transacao transacaoTransferencia = new Transacao(LocalDateTime.now(), valorTransferencia, conta.getSaldo(), TipoTransacao.MOVIMENTACAO, conta);
                                    repositoryTransacao.salvarTransacao(transacaoTransferencia);

                                }catch (IllegalArgumentException e){
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("\nTransferência bem sucedida!");
                                break;

                            case 4:
                                sairMenuConta = true;
                                break;

                            default:
                                System.out.println("\nOpção inválida!\n");
                                break;
                        }
                    }while (!sairMenuConta);
                    break;

                case 4:
                    System.out.println();
                    return;

                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        }while(true);
    }
}
