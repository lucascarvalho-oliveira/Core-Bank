package application.controller;

import model.Pessoa;
import model.Senha;
import repository.PessoaRepository;

import java.util.Scanner;

public class CooperadoController {
    private PessoaRepository repositoryPessoa;
    private Senha senha;

    public CooperadoController(PessoaRepository repositoryPessoa, Senha senha) {
        this.repositoryPessoa = repositoryPessoa;
        this.senha = senha;
    }

    public void cooperado(Scanner sc) {
        do {
            System.out.println("\nInforme o documento do cooperado.");
            String documento = sc.nextLine();

            Pessoa pessoa = repositoryPessoa.buscarPessoa(documento);

            if(pessoa == null){
                System.out.println("\nCooperado nao encontrado\n");
                return;
            }

            System.out.println("\n1 - Atualizar senha.");
            System.out.println("2 - Remover cooperado.");
            System.out.println("3 - Voltar ao menu anterior.");
            System.out.println("Escolha uma das opções acima.");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("Informe a antiga senha.");
                    String senhaDigitada = sc.nextLine();

                    if (!senha.validarSenha(pessoa, senhaDigitada)) {
                        System.out.println("Senha incorreta.");
                        break;
                    }

                    System.out.println("Informe a nova senha.");
                    String senhaNova = sc.nextLine();

                    try {
                        senha.confirmaSenha(senhaNova);

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    repositoryPessoa.atualizarSenha(documento, senhaNova);
                    break;

                case 2:
                    System.out.println("\nTem certeza que deseja remover o cooperado (s/n).");
                    String escolha = sc.nextLine();

                    if (escolha.equalsIgnoreCase("s")) {
                        repositoryPessoa.apagarPessoa(documento);
                    } else {
                        break;
                    }
                    break;

                case 3:
                    System.out.println();
                    return;

                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        } while (true);
    }
}
