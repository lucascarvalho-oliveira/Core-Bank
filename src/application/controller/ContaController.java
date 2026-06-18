package application.controller;

import model.*;
import model.enums.TipoConta;
import model.enums.TipoTransacao;
import repository.ContaRepository;
import repository.PixRepository;
import repository.TransacaoRepository;
import service.ContaService;
import service.InvestimentoService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ContaController {
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private InvestimentoService serviceInveste;
    private PixRepository repositoryPix;
    private TransacaoRepository repositoryTransacao;

    public ContaController(ContaService serviceConta, ContaRepository repositoryConta, InvestimentoService serviceInveste, PixRepository repositoryPix, TransacaoRepository repositoryTransacao){
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.serviceInveste = serviceInveste;
        this.repositoryPix = repositoryPix;
        this.repositoryTransacao = repositoryTransacao;
    }

    public void conta(Scanner sc, int id_pessoa){
        do{
            Conta conta = repositoryConta.buscarContaIdPessoa(id_pessoa);

            if(conta == null){
                System.out.println("Você precisa criar um conta primeiro!");
                return;
            }

            System.out.println("\n1 - Transações Bancarias:");
            System.out.println("2 - Ver histórico:");
            System.out.println("3 - Deletar Pix.");
            System.out.println("4 - Voltar:");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    boolean sairMenuConta = false;
                    do {
                        System.out.println("\nQual a transação bancaria você deseja fazer:");
                        System.out.println("Saldo em conta: R$ " + conta.getSaldo());

                        System.out.println("\n1 - Depositar:");
                        System.out.println("2 - Sacar:");
                        System.out.println("3 - Transferência via Pix:");
                        System.out.println("4 - Fazer um investimento bancário.");
                        System.out.println("5 - voltar:");
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
                                if(conta.getTipoConta() != TipoConta.CONTA_POUPANCA){
                                    System.out.println("So pode fazer investimento em conta poupança");
                                }

                                System.out.println("Informe o valor do investimento");
                                double valorInveste = sc.nextDouble();sc.nextLine();

                                if(valorInveste <= 0){
                                    System.out.println("Valor incorreto");
                                    break;
                                }

                                Investimento investimento = new Investimento(valorInveste, LocalDateTime.now(), conta);
                                serviceInveste.SalvarInvestimento(investimento, conta);

                                Transacao transacaoInveste = new Transacao(LocalDateTime.now(), valorInveste, conta.getSaldo(), TipoTransacao.INVESTIMENTO, conta);
                                repositoryTransacao.salvarTransacao(transacaoInveste);
                                break;

                            case 5:
                                sairMenuConta = true;
                                break;

                            default:
                                System.out.println("\nOpção inválida!\n");
                                break;
                        }
                    }while (!sairMenuConta);
                    break;

                case 2:
                    repositoryTransacao.extrato(conta.getIdConta());
                    break;

                case 3:
                    System.out.println("\nTem certeza que deseja apagar o pix (s/n)");
                    String escolha = sc.nextLine();

                    if(escolha.equalsIgnoreCase("s")){
                        System.out.println("\nInforme a chave pix.");
                        String chave = sc.nextLine();

                        repositoryPix.apagarPix(chave);
                    }else{
                        break;
                    }
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
