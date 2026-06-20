package application.controller;

import model.*;
import model.enums.TipoConta;
import model.enums.TipoPix;
import model.enums.TipoTransacao;
import repository.ContaRepository;
import repository.PixRepository;
import service.ContaService;
import service.TransacaoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ContaPixController {
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private PixRepository repositoryPix;
    private TransacaoService serviceTransacao;
    private CPF cpf;

    public ContaPixController(ContaService serviceConta, ContaRepository repositoryConta, PixRepository repositoryPix, TransacaoService serviceTransacao, CPF cpf){
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.repositoryPix = repositoryPix;
        this.serviceTransacao = serviceTransacao;
        this.cpf = cpf;
    }

    public void conta(Scanner sc, int idPessoa){
        do{
            List<Conta> contaEncontrada = repositoryConta.buscarContaIdPessoa(idPessoa);

            if(contaEncontrada.isEmpty()){
                System.out.println("Você não possui contas cadastradas.");
                return;
            }

            System.out.println("\nQual conta deseja realizar a operação.");
            for(int i = 0; i < contaEncontrada.size(); i++){
                System.out.println(i + 1 + " - " + contaEncontrada.get(i).getTipoConta() + " numero conta: "+ contaEncontrada.get(i).getIdConta());
            }
            int escolhido = sc.nextInt();sc.nextLine();

            if(escolhido <= 0 || escolhido > contaEncontrada.size()){
                System.out.println("Valor incorreto.");
                return;
            }

            Conta conta = contaEncontrada.get(escolhido - 1);

            System.out.println("\n1 - Transações Bancarias:");
            System.out.println("2 - Gestão Pix.");
            System.out.println("3 - Deletar Conta.");
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

                                    serviceTransacao.salvarTransacao(transacaoDepositar);

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
                                    serviceTransacao.salvarTransacao(transacaoSacar);

                                }catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    break;
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
                                    serviceTransacao.salvarTransacao(transacaoTransferencia);

                                }catch (IllegalArgumentException e){
                                    System.out.println(e.getMessage());
                                    break;
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

                case 2:
                    boolean sair = false;
                    do {
                        System.out.println("\n1 - Atualizar chave pix.");
                        System.out.println("2 - Apagar pix");
                        System.out.println("3 - Voltar ao menu anterior.");
                        System.out.println("Escolha uma das opções acima.");
                        int menuPix = sc.nextInt();
                        sc.nextLine();

                        switch (menuPix) {
                            case 1:
                                System.out.println("\nTem certeza que deseja apagar o pix (s/n)");
                                String escolhaPix = sc.nextLine();

                                if (escolhaPix.equalsIgnoreCase("s")) {
                                    System.out.println("\nInforme a chave pix.");
                                    String chave = sc.nextLine();

                                    repositoryPix.apagarPix(chave);
                                } else {
                                    break;
                                }
                                break;

                            case 2:
                                System.out.println("\nInforme a chave pix cadastrada.");
                                String chaveVelha = sc.nextLine();

                                System.out.println("\nescolha a nova chave pix");
                                System.out.println("1 - CPF");
                                System.out.println("2 - Telefone:");
                                System.out.println("3 - Email");
                                int menuEscolhaPix = sc.nextInt();
                                sc.nextLine();

                                TipoPix tipoPix = null;
                                String chave = "";

                                switch (menuEscolhaPix) {
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

                                if (tipoPix == null) {
                                    System.out.println("ERRO! ao salvar chave.");
                                    break;
                                }
                                repositoryPix.updateChave(chaveVelha, tipoPix, chave);
                                break;

                            case 3:
                                System.out.println();
                                sair = true;
                                break;

                            default:
                                System.out.println("\nOpção inválida!\n");
                                break;
                        }
                    }while (!sair);

                case 3:
                    System.out.println("\nTem certeza que deseja apagar conta (s/n)");
                    String escolhaConta = sc.nextLine();

                    if(escolhaConta.equalsIgnoreCase("n")){
                        break;
                    }

                    if(conta.getSaldo() == 0){
                        System.out.println("Cooperado precisa que o saldo esteja zerado.");
                        break;
                    }

                    repositoryConta.apagarConta(conta);
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
