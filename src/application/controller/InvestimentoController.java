package application.controller;

import model.Conta;
import model.Investimento;
import model.Transacao;
import model.enums.TipoConta;
import model.enums.TipoTransacao;
import repository.ContaRepository;
import repository.InvestimentoRepository;
import repository.TransacaoRepository;
import service.ContaService;
import service.InvestimentoService;
import service.TransacaoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class InvestimentoController {
    private InvestimentoService serviceInveste;
    private InvestimentoRepository repositoryInveste;
    private ContaService serviceConta;
    private ContaRepository repositoryConta;
    private TransacaoService serviceTransacao;

    public InvestimentoController(InvestimentoService serviceInveste, InvestimentoRepository repositoryInveste, ContaService serviceConta, ContaRepository repositoryConta, TransacaoService serviceTransacao){
        this.serviceInveste = serviceInveste;
        this.repositoryInveste = repositoryInveste;
        this.serviceConta = serviceConta;
        this.repositoryConta = repositoryConta;
        this.serviceTransacao = serviceTransacao;
    }

    public void investimento(Scanner sc, int idPessoa){
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
        if(conta.getTipoConta() != TipoConta.CONTA_POUPANCA){
            System.out.println("So pode fazer investimento em conta poupança");
            return;
        }

        do{
            System.out.println("1 - Fazer um novo investimento.");
            System.out.println("2 - Consultar investimento.");
            System.out.println("3 - Gestão do investimento.");
            System.out.println("4 - Voltar ao menu anterior.");
            System.out.println("Escolha uma das opções acima.");
            int menu = sc.nextInt();sc.nextLine();

            switch (menu){
                case 1:
                    System.out.println("\nInforme o valor do investimento");
                    double valorInveste = sc.nextDouble();sc.nextLine();

                    if(valorInveste <= 0){
                        System.out.println("Valor incorreto");
                        break;
                    }

                    serviceInveste.SalvarInvestimento(valorInveste, conta);

                    Transacao transacaoInveste = new Transacao(LocalDateTime.now(), valorInveste, conta.getSaldo(), TipoTransacao.INVESTIMENTO, conta);
                    serviceTransacao.salvarTransacao(transacaoInveste);
                    break;

                case 2:
                    repositoryInveste.consultarInveste(conta);
                    break;

                case 3:
                    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    List<Investimento> investEncontrado = repositoryInveste.buscarInvestimento(conta);

                    System.out.println("\nEscolha o investimento:\n");
                    for(int i = 0; i < investEncontrado.size(); i++){
                        System.out.println(i + 1 + " - " + "Valor: R$ " + investEncontrado.get(i).getValor() + " Data: " + investEncontrado.get(i).getDataAplicacao().format(formatador));
                    }
                    int escolhaInvest = sc.nextInt();sc.nextLine();

                    if(escolhaInvest <= 0 || escolhaInvest > investEncontrado.size()){
                        System.out.println("\nInvestimento incorreta\n");
                        break;
                    }

                    Investimento investimento = investEncontrado.get(escolhaInvest - 1);

                    boolean sair = false;
                    do {
                        System.out.println("\n1 - Aumentar valor do investimento.");
                        System.out.println("2 - Apagar Investimento.");
                        System.out.println("3 - voltar ao menu anterior.");
                        System.out.println("Escolha uma das opções acima.");
                        int menuGestao = sc.nextInt();
                        sc.nextLine();

                        switch (menuGestao) {
                            case 1:
                                System.out.println("\nInforme o valor que sera investido.");
                                double valorInvest = sc.nextDouble();
                                sc.nextLine();

                                try {
                                    serviceInveste.atualizarValor(valorInvest, investimento.getIdInvestimento(), conta);

                                }catch (IllegalArgumentException e){
                                    System.out.println(e.getMessage());
                                }

                                Transacao transacaoInvesteUP = new Transacao(LocalDateTime.now(), valorInvest, conta.getSaldo(), TipoTransacao.INVESTIMENTO, conta);
                                serviceTransacao.salvarTransacao(transacaoInvesteUP);
                                break;

                            case 2:
                                System.out.println("\nTem certeza que deseja apagar o investimento (s/n).");
                                String escolhaDelete = sc.nextLine();

                                if (escolhaDelete.equalsIgnoreCase("n")) {
                                    break;
                                }

                                try {
                                    serviceConta.depositar(conta, investimento.getValor());

                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }

                                serviceInveste.deletarInvest(investimento.getIdInvestimento());
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
                    break;

                case 4:
                    System.out.println();
                    return;
            }
        }while(true);
    }
}
