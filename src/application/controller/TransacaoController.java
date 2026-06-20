package application.controller;

import model.Conta;
import model.Transacao;
import repository.ContaRepository;
import repository.TransacaoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransacaoController {
    private TransacaoRepository repositoryTransacao;
    private ContaRepository repositoryConta;

    public TransacaoController(TransacaoRepository repositoryTransacao, ContaRepository repositoryConta){
        this.repositoryTransacao = repositoryTransacao;
        this.repositoryConta = repositoryConta;
    }

    public void transacao(Scanner sc, int idPessoa){
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

        do{
            System.out.println("1 - Histórico conta.");
            System.out.println("2 - Atualizar data e hora do histórico.");
            System.out.println("3 - Deletar histórico");
            System.out.println("4 - Voltar ao menu anterior");
            System.out.println("Escolha uma das opções acima.");
            int menuTransacao = sc.nextInt();sc.nextLine();

            switch (menuTransacao){
                case 1:
                    repositoryTransacao.extrato(conta.getIdConta());
                    break;

                case 2:
                    List<Transacao> transacaoEncontrada = repositoryTransacao.buscarTransacao(conta.getIdConta());
                    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                    System.out.println("\nEscolha qual histórico deseja atualizar a data e hora.");
                    for(int i = 0; i < transacaoEncontrada.size(); i++){
                        System.out.println(i + 1 + " - " + "Tipo de transação: " + transacaoEncontrada.get(i).getTipoTransacao() + "Data e hora: " + transacaoEncontrada.get(i).getData_hora().format(formatador));
                    }
                    int escolhaTransacao = sc.nextInt();

                    if(escolhaTransacao < 0 || escolhaTransacao > transacaoEncontrada.size()){
                        System.out.println("Escolha incorreto.");
                        break;
                    }

                    Transacao transacao = transacaoEncontrada.get(escolhaTransacao - 1);

                    System.out.println("\nInforme a data e a hora (dd/MM/yyyy HH:mm:ss).");
                    String dataDigitada = sc.nextLine();

                    LocalDateTime dataNova = LocalDateTime.parse(dataDigitada);

                    repositoryTransacao.updateData(dataNova, transacao.getIdTransacao());
                    break;

                case 3:
                    System.out.println("\nTem certeza que deseja apagar histórico (s/n)");
                    String escolhaConta = sc.nextLine();

                    if(escolhaConta.equalsIgnoreCase("n")){
                        break;
                    }

                    repositoryTransacao.apagarTransacao(conta.getIdConta());
                    break;

                case 4:
                    System.out.println();
                    return;

                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        }while (true);
    }
}
